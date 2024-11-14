package com.edsonlima.flixapp.presenter.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentMovieGenreBinding
import com.edsonlima.flixapp.presenter.main.home.adapter.MovieAdapter
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.initToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private lateinit var binding: FragmentMovieGenreBinding

    private val args: MovieGenreFragmentArgs by navArgs()

    private val movieGenreViewModel: MovieGenreViewModel by viewModels()

    private lateinit var movieGenreAdapter: MovieGenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieGenreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar(binding.tbMovieGenre)
        initRecycler()
        getMoviesByGenre()

    }

    private fun getMoviesByGenre() {

        movieGenreViewModel.getMoviesByGenreId(args.genreId)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {}
                    is StateView.Success -> {
                        binding.tbMovieGenre.title = args.name
                        movieGenreAdapter.submitList(stateView.data)
                    }

                    is StateView.Error -> {
                        Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

    }

    private fun initRecycler() {

        movieGenreAdapter = MovieGenreAdapter(requireContext())

        binding.rvMovieGenre.adapter = movieGenreAdapter

        binding.rvMovieGenre.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}