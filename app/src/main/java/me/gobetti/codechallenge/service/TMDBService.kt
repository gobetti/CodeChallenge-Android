package me.gobetti.codechallenge.service

import me.gobetti.codechallenge.model.TMDBResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Call<TMDBResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Call<TMDBResponse>

    companion object Factory {
        fun create() = create("https://api.themoviedb.org/3/")

        fun create(baseUrl: String): TMDBService {
            val client = OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(client)
                    .build()
                    .create(TMDBService::class.java)
        }
    }
}