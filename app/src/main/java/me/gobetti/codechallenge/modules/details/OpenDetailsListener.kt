package me.gobetti.codechallenge.modules.details

import me.gobetti.codechallenge.model.Movie

interface OpenDetailsListener {
    fun onDetailsRequested(movie: Movie)
}