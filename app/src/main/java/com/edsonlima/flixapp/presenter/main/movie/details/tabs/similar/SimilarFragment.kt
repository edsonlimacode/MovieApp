package com.edsonlima.flixapp.presenter.main.movie.details.tabs.similar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.databinding.FragmentSimilarBinding
import com.edsonlima.flixapp.presenter.main.movie.details.MovieDetailViewModel
import com.edsonlima.flixapp.presenter.main.movie.genre.MovieGenreViewModel
import com.edsonlima.flixapp.presenter.main.movie.genre.MovieGenreAdapter
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.navigateAnimated
import com.edsonlima.flixapp.utils.onApplyComponentWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimilarFragment : Fragment() {

    private val similarViewModel: SimilarViewModel by viewModels()

    private val movieDetailViewModel: MovieDetailViewModel by activityViewModels()

    private lateinit var binding: FragmentSimilarBinding

    private lateinit var movieGenreAdapter: MovieGenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSimilarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onApplyComponentWindowInsets(view = binding.rvMovieSimilar)

        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        movieDetailViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            getSimilar(movieId)
        }
    }

    private fun getSimilar(movieId: Int) {
        similarViewModel.getSimilarByMovieId(movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    movieGenreAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler() {

        movieGenreAdapter = MovieGenreAdapter(
            context = requireContext(),
            onClickListener = { movieId ->
                 val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                 findNavController().navigateAnimated(action)
            }
        )

        binding.rvMovieSimilar.adapter = movieGenreAdapter

        binding.rvMovieSimilar.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}