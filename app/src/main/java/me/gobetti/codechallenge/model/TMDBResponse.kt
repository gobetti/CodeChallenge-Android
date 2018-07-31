package me.gobetti.codechallenge.model

import com.squareup.moshi.Json

data class TMDBResponse(
        @field:Json(name = "results")
        val movies: List<Movie>,
        val page: Int,
        @field:Json(name = "total_results")
        val totalMovies: Int,
        val dates: Dates,
        @field:Json(name = "total_pages")
        val totalPages: Int
)

data class Movie(
        @field:Json(name = "vote_count")
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        @field:Json(name = "vote_average")
        val voteAverage: Double,
        val title: String,
        val popularity: Double,
        @field:Json(name = "poster_path")
        val posterPath: String,
        @field:Json(name = "original_language")
        val originalLanguage: String,
        @field:Json(name = "original_title")
        val originalTitle: String,
        @field:Json(name = "genre_ids")
        val genreIds: List<Int>,
        @field:Json(name = "backdrop_path")
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String,
        @field:Json(name = "release_date")
        val releaseDate: String
)

data class Dates(
        val maximum: String,
        val minimum: String
)