package com.edsonlima.flixapp.presenter.movie.movelist

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentMovieGenreBinding
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private lateinit var binding: FragmentMovieGenreBinding

    private val args: MovieGenreFragmentArgs by navArgs()

    private val movieViewModel: MovieViewModel by viewModels()

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
        initSearch()
        getMoviesByGenre()

    }

    private fun getMoviesByGenre() {

        movieViewModel.getMoviesByGenreId(args.genreId)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                        binding.pbSearch.isVisible = true

                        binding.rvMovieGenre.isVisible = false
                    }

                    is StateView.Success -> {
                        binding.pbSearch.isVisible = false

                        binding.tbMovieGenre.title = args.name

                        movieGenreAdapter.submitList(stateView.data)

                        binding.rvMovieGenre.isVisible = true
                    }

                    is StateView.Error -> {
                        binding.pbSearch.isVisible = false

                        binding.rvMovieGenre.isVisible = true

                        Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

    }

    private fun searchMovies(query: String) {

        movieViewModel.searchMoviesByName(query).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.pbSearch.isVisible = true
                    binding.rvMovieGenre.isVisible = false
                }

                is StateView.Success -> {
                    binding.pbSearch.isVisible = false
                    binding.rvMovieGenre.isVisible = true
                    movieGenreAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                    binding.pbSearch.isVisible = false
                    binding.rvMovieGenre.isVisible = true
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
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

        movieGenreAdapter = MovieGenreAdapter(
            context = requireContext(),
            onClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            }
        )

        binding.rvMovieGenre.adapter = movieGenreAdapter

        binding.rvMovieGenre.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}