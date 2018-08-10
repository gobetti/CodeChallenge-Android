package me.gobetti.codechallenge.modules.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.details.OpenDetailsListener
import me.gobetti.codechallenge.service.TMDBImageSize
import me.gobetti.codechallenge.service.loadFrom

class MoviesRecyclerAdapter(
        private val openDetailsListener: OpenDetailsListener
) : PagedListAdapter<Movie, MoviesRecyclerAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(p0: Movie, p1: Movie): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Movie, p1: Movie): Boolean {
                return p0 == p1
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.view_movie, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = getItem(position) ?: return

        viewHolder.itemView.setOnClickListener {
            openDetailsListener.onDetailsRequested(movie)
        }
        viewHolder.bind(movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImageView: ImageView = itemView.findViewById(R.id.movieImage)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieImageView.loadFrom(movie, TMDBImageSize.high)
        }
    }
}