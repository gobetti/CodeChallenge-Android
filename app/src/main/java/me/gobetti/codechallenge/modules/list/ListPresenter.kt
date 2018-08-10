package me.gobetti.codechallenge.modules.list

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.provider.SearchRecentSuggestions
import me.gobetti.codechallenge.di.serviceLocator
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.service.TMDBService
import me.gobetti.codechallenge.utils.RecentSearchesProvider

class ListPresenter(
        val view: ListContract.View,
        private val service: TMDBService = serviceLocator().tmdbService
): ListContract.Presenter {
    private val moviesPagedList = LivePagedListBuilder<Int, Movie>(
            MovieDataSourceFactory(service),
            PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(TMDBService.PAGE_SIZE)
                    .build())
            .build()

    private val suggestions by lazy {
        SearchRecentSuggestions(view.getContext(), RecentSearchesProvider.AUTHORITY, RecentSearchesProvider.MODE)
    }

    override fun fetchMovies() {
        moviesPagedList.observe(view, Observer<PagedList<Movie>> {
            view.displayPagedMovies(it)
        })
    }

    override fun searchMovies(query: String) {
        suggestions.saveRecentQuery(query, null)
        TODO("not implemented")
    }

    override fun clearSearchHistory() = suggestions.clearHistory()
}