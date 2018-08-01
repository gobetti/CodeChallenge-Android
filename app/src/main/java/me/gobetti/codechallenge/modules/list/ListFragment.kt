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

import kotlinx.android.synthetic.main.fragment_list.*
import me.gobetti.codechallenge.modules.details.OpenDetailsListener

class ListFragment : Fragment(), ListContract.View, OpenDetailsListener {
    private val presenter: ListContract.Presenter = ListPresenter(this)
    private lateinit var linearLayoutManager: LinearLayoutManager
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

        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
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

    // ListContract.View
    override fun displayMovies(movies: List<Movie>) {
        recyclerView.adapter = MoviesRecyclerAdapter(movies, this)
    }
}