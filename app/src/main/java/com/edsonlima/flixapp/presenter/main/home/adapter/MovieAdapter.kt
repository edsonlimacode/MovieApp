package com.edsonlima.flixapp.presenter.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.databinding.ItemMovieBinding
import com.edsonlima.flixapp.domain.model.Movie

class MovieAdapter(
    private val context: Context
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(
        val binding: ItemMovieBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(holder.binding.imgItemMovie)
    }
}