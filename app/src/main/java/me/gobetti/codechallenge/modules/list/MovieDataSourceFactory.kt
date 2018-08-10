package me.gobetti.codechallenge.modules.list

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.service.TMDBService

class MovieDataSourceFactory(
        private val service: TMDBService
): DataSource.Factory<Int, Movie>() {
    private val mutableLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        return MovieDataSource(service).also {
            mutableLiveData.postValue(it)
        }
    }
}