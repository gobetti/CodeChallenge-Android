package me.gobetti.codechallenge.modules.list

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import me.gobetti.codechallenge.model.Movie

interface ListContract {
    interface ViewModel {
        val movies: LiveData<PagedList<Movie>>

        fun fetchMovies()
        fun searchMovies(query: String)
        fun clearSearchHistory()
    }
}