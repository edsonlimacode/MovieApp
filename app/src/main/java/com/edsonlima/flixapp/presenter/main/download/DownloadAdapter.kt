package com.edsonlima.flixapp.presenter.main.download

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.databinding.ItemMovieDownloadBinding
import com.edsonlima.flixapp.utils.calculateFileSize
import com.edsonlima.flixapp.utils.calculateMovieTime

class DownloadAdapter(
    private val detailsOnClickListener: (Int) -> Unit,
    private val deleteOnClickListener: (Movie) -> Unit,
) : ListAdapter<Movie, DownloadAdapter.DownloadViewHolder>(itemCallback) {


    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class DownloadViewHolder(
        val binding: ItemMovieDownloadBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = ItemMovieDownloadBinding.inflate(inflater, parent, false)

        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        val movie = getItem(position)

        with(holder) {
            if (movie.posterPath?.isNotEmpty() == true) {
                Glide.with(holder.binding.root.context)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(holder.binding.ivPlay)
            } else {
                holder.binding.ivPlay.setImageResource(R.drawable.image_default)
            }

            binding.textMovie.text = movie.title
            binding.textDuration.text = movie.runtime?.calculateMovieTime()
            binding.textSize.text = movie.runtime?.toDouble()?.calculateFileSize()

            binding.cardView3.setOnClickListener {
                movie.id?.let {
                    detailsOnClickListener(movie.id)
                }
            }

            binding.btnDelete.setOnClickListener {
                movie?.let {
                    deleteOnClickListener(movie)
                }
            }
        }

    }

}