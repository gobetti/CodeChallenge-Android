package me.gobetti.codechallenge.modules.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.details.OpenDetailsListener
import me.gobetti.codechallenge.service.TMDBImageSize
import me.gobetti.codechallenge.service.loadFrom

class MoviesRecyclerAdapter(
        private val movies: List<Movie>,
        private val openDetailsListener: OpenDetailsListener
) : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.view_movie, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = movies[position]
        viewHolder.itemView.setOnClickListener {
            openDetailsListener.onDetailsRequested(movie)
        }
        viewHolder.bind(movie)
    }

    override fun getItemCount() = movies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImageView: ImageView = itemView.findViewById(R.id.movieImage)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieImageView.loadFrom(movie, TMDBImageSize.high)
        }
    }
}