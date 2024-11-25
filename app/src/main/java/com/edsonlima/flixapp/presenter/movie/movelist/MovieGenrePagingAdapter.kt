package com.edsonlima.flixapp.presenter.movie.movelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.ItemMovieGenreBinding
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.utils.circularProgressDrawable

class MovieGenrePagingAdapter(
    private val context: Context,
    private val onClickListener: (Int) -> Unit
) : PagingDataAdapter<Movie, MovieGenrePagingAdapter.MovieViewHolder>(diffCallback) {

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
        val binding: ItemMovieGenreBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val inflate = LayoutInflater.from(parent.context)

        val view = ItemMovieGenreBinding.inflate(
            inflate,
            parent,
            false
        )

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .placeholder(context.circularProgressDrawable())
            .into(holder.binding.imgItemMovie)

        holder.binding.cvMovieGenre.setOnClickListener {
            movie?.id?.let { movieId ->
                onClickListener(movieId)
            }
        }
    }
}