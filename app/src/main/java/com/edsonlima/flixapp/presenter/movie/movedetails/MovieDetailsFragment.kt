package com.edsonlima.flixapp.presenter.movie.movedetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.databinding.FragmentMovieDetailsBinding
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.initToolBar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        initToolBar(binding.tbMovieDetail)

        getMovieById(args.movieId)
    }

    private fun getMovieById(movieId: Int) {

        movieViewModel.getMovieById(movieId).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    stateView.data?.let { movie ->
                        setMovie(movie)
                    }
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    private fun setMovie(movie: Movie) {

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.imgDetail)

        binding.textTitleDetail.text = movie.title

        binding.textVoteAverage.text = String.format("%.1f", movie.voteAverage)

        binding.textCountry.text = movie.productionCountries?.get(0)?.name

        val data = LocalDate.parse(movie.releaseDate, DateTimeFormatter.ISO_DATE)

        binding.textYear.text = data.year.toString()
    }
}