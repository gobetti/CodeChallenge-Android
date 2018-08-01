package me.gobetti.codechallenge.modules.list

import android.util.Log
import me.gobetti.codechallenge.model.TMDBResponse
import me.gobetti.codechallenge.service.TMDBService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPresenter(val view: ListContract.View): ListContract.Presenter {
    private val service = TMDBService.create()

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
}