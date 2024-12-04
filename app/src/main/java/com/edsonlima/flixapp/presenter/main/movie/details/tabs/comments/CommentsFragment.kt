package com.edsonlima.flixapp.presenter.main.movie.details.tabs.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsonlima.flixapp.databinding.FragmentCommentsBinding
import com.edsonlima.flixapp.presenter.main.movie.details.MovieDetailViewModel
import com.edsonlima.flixapp.presenter.main.movie.genre.MovieGenreViewModel
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private lateinit var commentsAdapter: CommentsAdapter

    private lateinit var binding: FragmentCommentsBinding

    private val movieDetailViewModel: MovieDetailViewModel by activityViewModels()
    private val commentsViewModel: CommentsViewModel by viewModels()

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
        commentsAdapter = CommentsAdapter()

        binding.rvMovieComments.adapter = commentsAdapter

        binding.rvMovieComments.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObserver() {
        movieDetailViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            getComments(movieId)
        }
    }

    private fun getComments(movieId: Int) {
        commentsViewModel.getCommentsByMovieId(movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    commentsAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}