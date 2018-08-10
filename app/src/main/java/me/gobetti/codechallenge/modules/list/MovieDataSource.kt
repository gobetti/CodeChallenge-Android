package me.gobetti.codechallenge.modules.list

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.model.TMDBResponse
import me.gobetti.codechallenge.service.TMDBService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class RequestType {
    class Search(val query: String): RequestType()
    class Upcoming: RequestType()
}

class MovieDataSource(
        private val service: TMDBService,
        private val requestType: RequestType
): PageKeyedDataSource<Int, Movie>() {
    companion object {
        const val FIRST_PAGE = 1
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val page = FIRST_PAGE
        getUpcomingMovies(page) { movies, totalPages ->
            val adjacentPage = if (page < totalPages) page + 1 else null
            callback.onResult(movies, null, adjacentPage)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        getUpcomingMovies(page) { movies, totalPages ->
            val adjacentPage = if (page < totalPages) page + 1 else null
            callback.onResult(movies, adjacentPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        getUpcomingMovies(page) { movies, _ ->
            val adjacentPage = if (page > FIRST_PAGE) page - 1 else null
            callback.onResult(movies, adjacentPage)
        }
    }

    private fun getUpcomingMovies(page: Int, success: (List<Movie>, totalPages: Int) -> Unit) {
        val request = when (requestType) {
            is RequestType.Search -> service.searchMovies(requestType.query, page)
            is RequestType.Upcoming -> service.getUpcomingMovies(page)
        }

        request.enqueue(object: Callback<TMDBResponse> {
            override fun onResponse(call: Call<TMDBResponse>?, response: Response<TMDBResponse>?) {
                val body = response?.body()
                val movies = body?.movies
                if (movies == null) {
                    Log.e("getUpcomingMovies", "movies is null")
                    return
                }
                success(movies, body.totalPages)
            }

            override fun onFailure(call: Call<TMDBResponse>?, t: Throwable?) {
                Log.e("getUpcomingMovies", "onFailure")
            }
        })
    }
}