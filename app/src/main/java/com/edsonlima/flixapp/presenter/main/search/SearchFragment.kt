package com.edsonlima.flixapp.presenter.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.databinding.FragmentSearchBinding
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.presenter.movie.movelist.LoadStatePagingAdapter
import com.edsonlima.flixapp.presenter.movie.movelist.MovieGenreAdapter
import com.edsonlima.flixapp.presenter.movie.movelist.MovieGenrePagingAdapter
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    private lateinit var movieGenrePagingAdapter: MovieGenrePagingAdapter

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
    }

    private fun initSearchBar() {

        binding.svSearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    hideKeyboard()
                    searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun searchMovies(query: String) {

        lifecycleScope.launch {
            searchViewModel.searchMoviesByName(query).collectLatest {
                movieGenrePagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecycler() {

        movieGenrePagingAdapter = MovieGenrePagingAdapter(
            context = requireContext(),
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            }
        )

        lifecycleScope.launch {
            movieGenrePagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.rvMovieSearch.isVisible = false
                        binding.pbSearch.isVisible = true
                    }

                    is LoadState.NotLoading -> {
                        binding.rvMovieSearch.isVisible = true
                        binding.pbSearch.isVisible = false
                    }

                    is LoadState.Error -> {
                        binding.rvMovieSearch.isVisible = false
                        binding.pbSearch.isVisible = false

                        val error = (loadState.refresh as LoadState.Error).error.message

                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvMovieSearch.layoutManager = gridLayoutManager

        val searchAdapter = movieGenrePagingAdapter.withLoadStateFooter(
            footer = LoadStatePagingAdapter()
        )

        binding.rvMovieSearch.adapter = searchAdapter

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == movieGenrePagingAdapter.itemCount && searchAdapter.itemCount > 0) {
                    2
                } else {
                    1
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