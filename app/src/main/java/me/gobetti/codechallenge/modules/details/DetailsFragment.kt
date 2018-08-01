package me.gobetti.codechallenge.modules.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie

class DetailsFragment : Fragment(), DetailsContract.View {
    companion object {
        private const val MOVIE_KEY = "MOVIE_KEY"

        fun newInstance(movie: Movie): DetailsFragment {
            val args = Bundle()
            args.putSerializable(MOVIE_KEY, movie)

            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var movie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movie = arguments!!.getSerializable(MOVIE_KEY) as Movie
        Log.d("Details", movie.title)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }
}