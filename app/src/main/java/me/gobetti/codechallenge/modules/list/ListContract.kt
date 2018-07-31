package me.gobetti.codechallenge.modules.list

import me.gobetti.codechallenge.model.Movie

interface ListContract {
    interface View {
        fun displayMovies(movies: List<Movie>)
    }

    interface Presenter {
        fun fetchMovies()
    }
}