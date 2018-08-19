package me.gobetti.codechallenge.modules.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
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
    private val suggestions by lazy {
        SearchRecentSuggestions(view.getContext(), RecentSearchesProvider.AUTHORITY, RecentSearchesProvider.MODE)
    }

    private val pagedList = MediatorLiveData<PagedList<Movie>>().apply {
        observe(view, Observer<PagedList<Movie>> {
            view.displayPagedMovies(it)
        })
    }

    private fun createMoviesPagedList(requestType: RequestType) = LivePagedListBuilder<Int, Movie>(
            MovieDataSourceFactory(service, requestType),
            PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(TMDBService.PAGE_SIZE)
                    .build())
            .build()

    private fun setLiveDataSource(liveData: LiveData<PagedList<Movie>>) = pagedList.addSource(liveData) {
        pagedList.value = it
    }

    // ListContract.Presenter
    override fun fetchMovies() {
        setLiveDataSource(createMoviesPagedList(RequestType.Upcoming()))
    }

    override fun searchMovies(query: String) {
        suggestions.saveRecentQuery(query, null)
        setLiveDataSource(createMoviesPagedList(RequestType.Search(query)))
    }

    override fun clearSearchHistory() = suggestions.clearHistory()
}