package com.edsonlima.flixapp.presenter.movie.movedetails.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentCommentsBinding
import com.edsonlima.flixapp.domain.model.AuthorDetails
import com.edsonlima.flixapp.domain.model.MovieReview
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.presenter.movie.movedetails.MovieCommentsAdapter
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private lateinit var movieCommentsAdapter: MovieCommentsAdapter

    private lateinit var binding: FragmentCommentsBinding

    private val movieViewModel: MovieViewModel by activityViewModels()

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
        initObserver()
    }

    private fun initRecycler() {
        movieCommentsAdapter = MovieCommentsAdapter()

        binding.rvMovieComments.adapter = movieCommentsAdapter

        binding.rvMovieComments.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObserver() {
        movieViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            getComments(movieId)
        }
    }

    private fun getComments(movieId: Int) {
        movieViewModel.getCommentsByMovieId(movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    movieCommentsAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}