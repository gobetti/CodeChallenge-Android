package me.gobetti.codechallenge.modules.list

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.model.TMDBResponse
import me.gobetti.codechallenge.service.TMDBService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataSource(
        private val service: TMDBService
): PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val page = 1
        getUpcomingMovies(page) {
            callback.onResult(it, null, page + 1)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        getUpcomingMovies(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        getUpcomingMovies(page) {
            callback.onResult(it, page - 1)
        }
    }

    private fun getUpcomingMovies(page: Int, success: (List<Movie>) -> Unit) {
        service.getUpcomingMovies(page).enqueue(object: Callback<TMDBResponse> {
            override fun onResponse(call: Call<TMDBResponse>?, response: Response<TMDBResponse>?) {
                val movies = response?.body()?.movies
                if (movies == null) {
                    Log.e("getUpcomingMovies", "movies is null")
                    return
                }
                success(movies)
            }

            override fun onFailure(call: Call<TMDBResponse>?, t: Throwable?) {
                Log.e("getUpcomingMovies", "onFailure")
            }
        })
    }
}