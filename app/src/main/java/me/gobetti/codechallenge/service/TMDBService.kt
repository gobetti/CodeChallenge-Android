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
    fun getUpcomingMovies(@Query("page") page: Int): Call<TMDBResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String,
                     @Query("page") page: Int): Call<TMDBResponse>

    companion object {
        const val PAGE_SIZE = 20

        fun create(baseUrl: String = "https://api.themoviedb.org/3/"): TMDBService {
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