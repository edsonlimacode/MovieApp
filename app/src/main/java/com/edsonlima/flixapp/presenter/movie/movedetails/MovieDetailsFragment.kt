package com.edsonlima.flixapp.presenter.movie.movedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.edsonlima.flixapp.databinding.FragmentMovieDetailsBinding
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.presenter.movie.movelist.MovieGenreFragmentArgs
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovie(args.movieId)
    }

    private fun setMovie(movieId: Int) {

        movieViewModel.getMovieById(movieId).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    Log.i("movie", "setMovie: ${stateView.data}")
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}