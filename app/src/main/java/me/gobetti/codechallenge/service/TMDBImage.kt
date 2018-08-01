package me.gobetti.codechallenge.service

import android.widget.ImageView
import com.squareup.picasso.Picasso
import me.gobetti.codechallenge.model.Movie

private const val BASE_URL = "https://image.tmdb.org/t/p"

enum class TMDBImageSize(val width: Int) {
    lowest(92),
    low(154),
    mid(185),
    regular(342),
    high(500),
    highest(780)
}

fun ImageView.loadFrom(movie: Movie, size: TMDBImageSize? = null) {
    val widthParam = size?.let { "w${it.width}" } ?: "original"
    val imagePath = movie.posterPath ?: movie.backdropPath
    val url = "$BASE_URL/$widthParam$imagePath"
    Picasso.get().load(url).into(this)
}