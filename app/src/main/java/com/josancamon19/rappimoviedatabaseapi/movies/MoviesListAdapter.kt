package com.josancamon19.rappimoviedatabaseapi.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.rappimoviedatabaseapi.R
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import com.josancamon19.rappimoviedatabaseapi.databinding.ListItemBinding

class MoviesListAdapter(private var onMovieClickListener: OnMovieClickListener) :
    ListAdapter<Movie, MoviesListAdapter.MoviesViewHolder>(DiffCallBackMovies()) {

    interface OnMovieClickListener {
        fun setOnMovieClickListener(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MoviesViewHolder(ListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClickListener)
    }

    class MoviesViewHolder(private val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie, onMovieClickListener: OnMovieClickListener) {
            itemBinding.movie = movie
            itemBinding.root.setOnClickListener {
                onMovieClickListener.setOnMovieClickListener(movie)
            }
            itemBinding.executePendingBindings()
        }
    }
}


class DiffCallBackMovies : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}