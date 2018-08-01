package me.gobetti.codechallenge.modules.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.gobetti.codechallenge.R
import me.gobetti.codechallenge.model.Movie

class MoviesRecyclerAdapter(private val movies: List<Movie>)
    : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.view_movie, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie = movies[position]
        viewHolder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
        }
    }
}