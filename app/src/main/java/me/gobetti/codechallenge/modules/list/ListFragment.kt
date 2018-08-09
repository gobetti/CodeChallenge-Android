package me.gobetti.codechallenge.modules.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.details.OpenDetailsListener

class ListFragment : Fragment(), ListContract.View, OpenDetailsListener, ScrolledToEndListener {
    private val presenter: ListContract.Presenter = ListPresenter(this)
    private val moviesAdapter = MoviesRecyclerAdapter(this, this)
    private var openDetailsListener: OpenDetailsListener? = null

    fun onSearchAction(query: String) {
        presenter.searchMovies(query)
    }

    fun onSearchEnded() {
        presenter.fetchMovies()
    }

    fun onSearchHistoryClearAction() {
        presenter.clearSearchHistory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchMovies()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OpenDetailsListener) {
            openDetailsListener = context
        }
    }

    // OpenDetailsListener
    override fun onDetailsRequested(movie: Movie) {
        openDetailsListener?.onDetailsRequested(movie)
    }

    // ScrolledToEndListener
    override fun onScrolledToEnd() {
        presenter.fetchMoreMovies()
    }

    // ListContract.View
    override fun displayMovies(movies: List<Movie>) {
        moviesAdapter.movies = movies
    }
}