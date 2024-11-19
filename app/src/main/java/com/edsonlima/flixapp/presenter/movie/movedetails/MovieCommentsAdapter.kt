package com.edsonlima.flixapp.presenter.movie.movedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.ItemMovieReviewBinding
import com.edsonlima.flixapp.domain.model.MovieReview
import com.edsonlima.flixapp.utils.formatCommentDate

class MovieCommentsAdapter :
    ListAdapter<MovieReview, MovieCommentsAdapter.MovieCommentsViewHolder>(itemCallback) {

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<MovieReview>() {
            override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MovieCommentsViewHolder(
        val binding: ItemMovieReviewBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCommentsViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = ItemMovieReviewBinding.inflate(inflater, parent, false)

        return MovieCommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCommentsViewHolder, position: Int) {
        val movieReview = getItem(position)

        with(holder) {

            if (movieReview.authorDetails?.avatarPath?.isNotEmpty() == true) {
                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/w500${movieReview.authorDetails.avatarPath}")
                    .into(binding.imgPersonReview)
            } else {
                binding.imgPersonReview.setImageResource(R.drawable.image_default)
            }

            binding.textPersonName.text = movieReview.author
            binding.textDescriptionReview.text = movieReview.content
            binding.textRatingNumber.text = (movieReview.authorDetails?.rating ?: 0).toString()
            binding.textRatingDate.text = formatCommentDate(movieReview.createdAt)

        }
    }
}