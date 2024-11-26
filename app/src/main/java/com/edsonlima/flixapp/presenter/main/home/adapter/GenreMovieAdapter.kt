package com.edsonlima.flixapp.presenter.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edsonlima.flixapp.databinding.ItemGenreMovieBinding
import com.edsonlima.flixapp.presenter.model.MovieByGenre

class GenreMovieAdapter(
    private val onClick: (Int, String) -> Unit,
    private val onClickListener: (Int) -> Unit
) : ListAdapter<MovieByGenre, GenreMovieAdapter.MovieViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieByGenre>() {
            override fun areItemsTheSame(
                oldItem: MovieByGenre,
                newItem: MovieByGenre
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieByGenre,
                newItem: MovieByGenre
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemGenreMovieBinding
    ) : ViewHolder(binding.root) {

        fun bind(genre: MovieByGenre) {

            binding.textCategoryTitle.text = genre.name.toString()

            val movieAdapter = MovieAdapter(
                context = binding.root.context,
                onClickListener = onClickListener
            )

            binding.rvGenre.adapter = movieAdapter

            movieAdapter.submitList(genre.movies)


            binding.btnSeeAll.setOnClickListener {
                onClick(genre.id!!, genre.name!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemGenreMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}