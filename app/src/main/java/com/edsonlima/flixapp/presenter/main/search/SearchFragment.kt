package com.edsonlima.flixapp.presenter.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.databinding.FragmentSearchBinding
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.presenter.movie.movelist.MovieGenreAdapter
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    private lateinit var movieGenreAdapter: MovieGenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initSearchBar()
        getMovieObserver()
    }

    private fun initSearchBar() {

        binding.svSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    hideKeyboard()
                    searchViewModel.searchMoviesByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun initRecycler() {
        movieGenreAdapter = MovieGenreAdapter(
            context = requireContext(),
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            }
        )

        binding.rvMovieSearch.adapter = movieGenreAdapter

        binding.rvMovieSearch.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    private fun getMovieObserver() {

        searchViewModel.movieList.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.pbSearch.isVisible = true
                    binding.rvMovieSearch.isVisible = false
                }

                is StateView.Success -> {
                    movieGenreAdapter.submitList(stateView.data)

                    stateView.data?.let {
                        isSearchResultEmpty(stateView.data.isEmpty())
                    }
                }

                is StateView.Error -> {
                    binding.pbSearch.isVisible = false
                    binding.rvMovieSearch.isVisible = true
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun isSearchResultEmpty(empty: Boolean) {
        binding.pbSearch.isVisible = false
        binding.rvMovieSearch.isVisible = !empty
        binding.llListEmpty.isVisible = empty
    }
}