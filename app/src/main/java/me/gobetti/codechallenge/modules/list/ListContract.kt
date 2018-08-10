package me.gobetti.codechallenge.modules.list

import android.arch.paging.PagedList
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.BaseMVPView

interface ListContract {
    interface View: BaseMVPView {
        fun displayPagedMovies(movies: PagedList<Movie>?)
    }

    interface Presenter {
        fun fetchMovies()
        fun searchMovies(query: String)
        fun clearSearchHistory()
    }
}