package com.edsonlima.flixapp.presenter.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.databinding.FragmentHomeBinding
import com.edsonlima.flixapp.presenter.main.home.adapter.GenreMovieAdapter
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.navigateAnimated
import com.edsonlima.flixapp.utils.onApplyComponentWindowInsets
import com.edsonlima.flixapp.utils.onApplyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var genreMovieAdapter: GenreMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getGenres()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onApplyWindowInsets(view = view, applyBottom = false)

        initRecyclerView()

        initObservers()
    }

    private fun initObservers() {
        homeViewModel.homeState.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.pbHome.isVisible = true
                    binding.rvHome.isVisible = false
                }
                is StateView.Success -> {
                    binding.pbHome.isVisible = false
                    binding.rvHome.isVisible = true
                }
                is StateView.Error -> {
                    binding.pbHome.isVisible = false
                    binding.rvHome.isVisible = true
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        homeViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            genreMovieAdapter.submitList(movieList)
        }
    }


    private fun initRecyclerView() {

        genreMovieAdapter = GenreMovieAdapter(
            onClick = { genreId, name ->
                val action =
                    HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, name)
                findNavController().navigateAnimated(action)
            },
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigateAnimated(action)
            }
        )

        binding.rvHome.adapter = genreMovieAdapter
    }

}