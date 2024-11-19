package com.edsonlima.flixapp.presenter.movie.movedetails.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentCommentsBinding
import com.edsonlima.flixapp.domain.model.AuthorDetails
import com.edsonlima.flixapp.domain.model.MovieReview
import com.edsonlima.flixapp.presenter.movie.movedetails.MovieCommentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private lateinit var movieCommentsAdapter: MovieCommentsAdapter

    private lateinit var binding: FragmentCommentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCommentsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        getComments()
    }

    private fun initRecycler() {
        movieCommentsAdapter = MovieCommentsAdapter()

        binding.rvMovieComments.adapter = movieCommentsAdapter

        binding.rvMovieComments.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getComments() {
        movieCommentsAdapter.submitList(fakeList())
    }

    private fun fakeList(): List<MovieReview> {
        return listOf(
            MovieReview(
                author = "thealanfrench",
                authorDetails = AuthorDetails(
                    name = "",
                    username = "thealanfrench",
                    avatarPath = "/4KVM1VkqmXLOuwj1jjaSdxbvBDk.jpg",
                    rating = 5
                ),
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                createdAt = "2023-03-15T05:13:49.138Z",
                id = "6411540dfe6c1800bb659ebd",
                updatedAt = "2023-03-15T05:13:49.138Z",
                url = "https://www.themoviedb.org/review/6411540dfe6c1800bb659ebd"
            )
        )
    }
}