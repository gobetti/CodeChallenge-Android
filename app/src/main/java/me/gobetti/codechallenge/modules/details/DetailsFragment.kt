package me.gobetti.codechallenge.modules.details

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie

import kotlinx.android.synthetic.main.fragment_details.*
import me.gobetti.codechallenge.service.TMDBImageSize
import me.gobetti.codechallenge.service.loadFrom

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
    val detailedMovie: Movie
        get() = movie
    private var isFullScreen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movie = arguments!!.getSerializable(MOVIE_KEY) as Movie
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateScreenElements()

        moviePosterImageView.setOnClickListener {
            isFullScreen = !isFullScreen
            val params = moviePosterImageView.layoutParams as ConstraintLayout.LayoutParams
            params.bottomToBottom = if (isFullScreen) detailsConstraintLayout.id else guideline.id
            moviePosterImageView.requestLayout()
        }
    }

    private fun populateScreenElements() {
        moviePosterImageView.loadFrom(movie, TMDBImageSize.highest)
        movieOverviewTextView.text = movie.overview
    }
}