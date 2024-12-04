package com.edsonlima.flixapp.presenter.main.movie.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentMovieGenreBinding
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import com.edsonlima.flixapp.utils.navigateAnimated
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private lateinit var binding: FragmentMovieGenreBinding

    private val args: MovieGenreFragmentArgs by navArgs()

    private val movieGenreViewModel: MovieGenreViewModel by viewModels()

    private lateinit var movieGenrePagingAdapter: MovieGenrePagingAdapter

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
        binding.tbMovieGenre.title = args.name

        initRecycler()
        initSearch()
        getMoviesByGenre()

    }

    private fun getMoviesByGenre() {

        movieGenreViewModel.getMoviesByGenreId(args.genreId)

        lifecycleScope.launch {
            movieGenreViewModel.movieList.collect {
                movieGenrePagingAdapter.submitData(it)
            }
        }
    }

    private fun searchMovies(query: String) {

        lifecycleScope.launch {
            movieGenreViewModel.searchMoviesByName(query).collectLatest {
                movieGenrePagingAdapter.submitData(it)
            }
        }
    }

    private fun initSearch() {

        initMenuSearch()

        binding.searchMovieGenre.setOnQueryTextListener(object :
            SimpleSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isNotEmpty()) {
                    hideKeyboard()

                    searchMovies(query)
                    return true
                } else {
                    return false
                }

            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.searchMovieGenre.setOnSearchViewListener(object :
            SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                binding.rvMovieGenre.isVisible = false
            }

            override fun onSearchViewClosed() {
                binding.pbSearch.isVisible = false
                binding.rvMovieGenre.isVisible = true
                getMoviesByGenre()
            }

            override fun onSearchViewShownAnimation() {}
            override fun onSearchViewClosedAnimation() {}
        })
    }

    private fun initMenuSearch() {

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_search, menu)

                val item = menu.findItem(R.id.action_search)
                binding.searchMovieGenre.setMenuItem(item)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initRecycler() {

        movieGenrePagingAdapter = MovieGenrePagingAdapter(
            context = requireContext(),
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigateAnimated(action)
            }
        )

        lifecycleScope.launch {
            movieGenrePagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.rvMovieGenre.isVisible = false
                        binding.pbSearch.isVisible = true
                    }

                    is LoadState.NotLoading -> {
                        binding.rvMovieGenre.isVisible = true
                        binding.pbSearch.isVisible = false
                    }

                    is LoadState.Error -> {
                        binding.rvMovieGenre.isVisible = false
                        binding.pbSearch.isVisible = false

                        val error = (loadState.refresh as LoadState.Error).error.message

                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvMovieGenre.layoutManager = gridLayoutManager

        val footerLayout = movieGenrePagingAdapter.withLoadStateFooter(
            footer = LoadStatePagingAdapter()
        )

        binding.rvMovieGenre.adapter = footerLayout

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == movieGenrePagingAdapter.itemCount && footerLayout.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
    }
}