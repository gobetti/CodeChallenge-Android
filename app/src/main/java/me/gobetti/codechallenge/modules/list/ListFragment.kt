package me.gobetti.codechallenge.modules.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie

class ListFragment : Fragment(), ListContract.View {
    private lateinit var presenter: ListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = ListPresenter(this)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun displayMovies(movies: List<Movie>) {
        TODO("not implemented")
    }
}