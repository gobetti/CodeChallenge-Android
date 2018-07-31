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
            return Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(TMDBService::class.java)
        }
    }
}