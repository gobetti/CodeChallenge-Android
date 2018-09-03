package me.gobetti.codechallenge.modules.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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

class ListFragment : Fragment(), OpenDetailsListener {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ListViewModel::class.java)
    }
    private val moviesAdapter = MoviesRecyclerAdapter(this)
    private var openDetailsListener: OpenDetailsListener? = null

    fun onSearchAction(query: String) {
        viewModel.searchMovies(query)
    }

    fun onSearchEnded() {
        viewModel.fetchMovies()
    }

    fun onSearchHistoryClearAction() {
        viewModel.clearSearchHistory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.movies.observe(viewLifecycleOwner, Observer(moviesAdapter::submitList))
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
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
}