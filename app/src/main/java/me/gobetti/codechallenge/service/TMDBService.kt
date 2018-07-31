package me.gobetti.codechallenge.service

import me.gobetti.codechallenge.model.TMDBResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface TMDBService {
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Call<TMDBResponse>

    companion object Factory {
        fun create(): TMDBService {
            return create("https://api.themoviedb.org/3/")
        }

        fun create(baseUrl: String): TMDBService {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(TMDBService::class.java)
        }
    }
}