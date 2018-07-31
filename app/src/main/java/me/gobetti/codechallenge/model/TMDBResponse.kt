package me.gobetti.codechallenge.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBResponse(
        @Json(name = "results")
        val movies: List<Movie>,
        val page: Int,
        @Json(name = "total_results")
        val totalMovies: Int,
        val dates: Dates,
        @Json(name = "total_pages")
        val totalPages: Int
)

@JsonClass(generateAdapter = true)
data class Movie(
        @Json(name = "vote_count")
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        @Json(name = "vote_average")
        val voteAverage: Double,
        val title: String,
        val popularity: Double,
        @Json(name = "poster_path")
        val posterPath: String,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "backdrop_path")
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String,
        @Json(name = "release_date")
        val releaseDate: String
)

@JsonClass(generateAdapter = true)
data class Dates(
        val maximum: String,
        val minimum: String
)