package com.edsonlima.flixapp.presenter.main.movie.details.tabs.trailer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsonlima.flixapp.databinding.FragmentTrailerBinding
import com.edsonlima.flixapp.presenter.main.movie.details.MovieDetailViewModel
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailerFragment : Fragment() {

    private lateinit var binding: FragmentTrailerBinding

    private val movieDetailViewModel: MovieDetailViewModel by activityViewModels()

    private val trailerViewModel: TrailerViewModel by viewModels()

    private lateinit var trailerAdapter: TrailerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrailerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        movieDetailViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            getTrailersByMovieId(movieId)
        }

        trailerViewModel.trailers.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    stateView.data?.let { trailers ->
                        trailerAdapter.submitList(trailers)
                    }
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getTrailersByMovieId(movieId: Int) {
        trailerViewModel.getTrailersByMovieId(movieId)
    }

    private fun initRecycler() {

        trailerAdapter = TrailerAdapter()

        binding.rvMovieTrailer.adapter = trailerAdapter

        binding.rvMovieTrailer.layoutManager = LinearLayoutManager(requireContext())

    }

}