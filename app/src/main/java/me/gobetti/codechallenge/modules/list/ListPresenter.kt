package me.gobetti.codechallenge.modules.list

import android.util.Log
import me.gobetti.codechallenge.model.TMDBResponse
import me.gobetti.codechallenge.service.TMDBService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.SearchRecentSuggestions
import me.gobetti.codechallenge.utils.RecentSearchesProvider

class ListPresenter(val view: ListContract.View): ListContract.Presenter {
    private val service = TMDBService.create()
    private val suggestions by lazy {
        SearchRecentSuggestions(view.getContext(), RecentSearchesProvider.AUTHORITY, RecentSearchesProvider.MODE)
    }

    override fun fetchMovies() {
        service.getUpcomingMovies().enqueue(object: Callback<TMDBResponse> {
            override fun onResponse(call: Call<TMDBResponse>?, response: Response<TMDBResponse>?) {
                val movies = response?.body()?.movies
                if (movies == null) {
                    Log.e("getUpcomingMovies", "movies is null")
                    return
                }
                view.displayMovies(movies)
            }

            override fun onFailure(call: Call<TMDBResponse>?, t: Throwable?) {
                Log.e("getUpcomingMovies", "onFailure")
            }
        })
    }

    override fun searchMovies(query: String) {
        suggestions.saveRecentQuery(query, null)

        service.searchMovies(query).enqueue(object: Callback<TMDBResponse> {
            override fun onResponse(call: Call<TMDBResponse>?, response: Response<TMDBResponse>?) {
                val movies = response?.body()?.movies
                if (movies == null) {
                    Log.e("searchMovies", "movies is null")
                    return
                }
                view.displayMovies(movies)
            }

            override fun onFailure(call: Call<TMDBResponse>?, t: Throwable?) {
                Log.e("searchMovies", "onFailure")
            }
        })
    }

    override fun clearSearchHistory() = suggestions.clearHistory()
}