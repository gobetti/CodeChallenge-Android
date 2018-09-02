package me.gobetti.codechallenge.modules.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.provider.SearchRecentSuggestions
import me.gobetti.codechallenge.di.serviceLocator
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.service.TMDBService
import me.gobetti.codechallenge.utils.RecentSearchesProvider

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val service = serviceLocator().tmdbService
    private val suggestions by lazy {
        SearchRecentSuggestions(application, RecentSearchesProvider.AUTHORITY, RecentSearchesProvider.MODE)
    }

    private var hasStarted = false
    private val pagedList = MediatorLiveData<PagedList<Movie>>()
    val movies: LiveData<PagedList<Movie>>
        get() = pagedList

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

    fun start() {
        if (hasStarted) return
        hasStarted = true
        fetchMovies()
    }

    fun fetchMovies() {
        setLiveDataSource(createMoviesPagedList(RequestType.Upcoming()))
    }

    fun searchMovies(query: String) {
        suggestions.saveRecentQuery(query, null)
        setLiveDataSource(createMoviesPagedList(RequestType.Search(query)))
    }

    fun clearSearchHistory() = suggestions.clearHistory()
}