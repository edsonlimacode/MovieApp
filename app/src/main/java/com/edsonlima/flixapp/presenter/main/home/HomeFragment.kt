package com.edsonlima.flixapp.presenter.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.databinding.FragmentHomeBinding
import com.edsonlima.flixapp.presenter.main.home.adapter.GenreMovieAdapter
import com.edsonlima.flixapp.presenter.model.GenrePresentation
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var genreMovieAdapter: GenreMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        getGenres()
    }

    private fun getGenres() {
        homeViewModel.getGenres().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {

                    stateView.data?.let {
                        genreMovieAdapter.submitList(it)
                        getMoviesByGenre(it)
                    }
                }

                is StateView.Error -> {}
            }
        }
    }

    private fun getMoviesByGenre(genres: List<GenrePresentation>) {

        val genreMultabaleList = genres.toMutableList()

        genreMultabaleList.forEachIndexed { index, genre ->
            homeViewModel.getMoviesByGenreId(genre.id!!).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {}
                    is StateView.Success -> {
                        genreMultabaleList[index] = genre.copy(movies = stateView.data?.take(10))

                        lifecycleScope.launch {
                            delay(1000)
                            genreMovieAdapter.submitList(genreMultabaleList)
                        }
                    }

                    is StateView.Error -> {
                        Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {

        genreMovieAdapter = GenreMovieAdapter(
            onClick = { genreId, name ->
                val action =
                    HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, name)
                findNavController().navigate(action)
            },
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            }
        )

        binding.rvHome.adapter = genreMovieAdapter

    }

}