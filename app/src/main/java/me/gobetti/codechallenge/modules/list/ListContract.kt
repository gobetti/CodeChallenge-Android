package me.gobetti.codechallenge.modules.list

import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.BaseMVPView

interface ListContract {
    interface View: BaseMVPView {
        fun displayMovies(movies: List<Movie>)
    }

    interface Presenter {
        fun fetchMovies()
        fun searchMovies(query: String)
    }
}