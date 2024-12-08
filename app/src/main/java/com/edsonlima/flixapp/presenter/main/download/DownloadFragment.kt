package com.edsonlima.flixapp.presenter.main.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.MainGraphDirections
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.BottomSheetDeleteMovieBinding
import com.edsonlima.flixapp.databinding.FragmentDownloadBinding
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.utils.calculateFileSize
import com.edsonlima.flixapp.utils.calculateMovieTime
import com.edsonlima.flixapp.utils.hideKeyboard
import com.edsonlima.flixapp.utils.initToolBar
import com.edsonlima.flixapp.utils.navigateAnimated
import com.edsonlima.flixapp.utils.onApplyWindowInsets
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding

    private lateinit var downloadAdapter: DownloadAdapter

    private val downloadViewModel: DownloadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDownloadBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onApplyWindowInsets(view = view, applyBottom = false)

        initToolBar(binding.tbMovieDownload, false)
        initRecycler()
        getMovies()
        initObservers()
        initSearch()
    }

    private fun getMovies() {
        downloadViewModel.getMovies()
    }

    private fun initObservers() {
        downloadViewModel.movieList.observe(viewLifecycleOwner) {
            downloadAdapter.submitList(it)
            isSearchResultEmpty(it.isEmpty())
        }

        downloadViewModel.movieSearchList.observe(viewLifecycleOwner) {
            downloadAdapter.submitList(it)
            isSearchResultEmpty(it.isEmpty())
        }
    }

    private fun initSearch() {

        initMenuSearch()

        binding.searchMovieDownload.setOnQueryTextListener(object :
            SimpleSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotBlank() || newText.isEmpty()) {
                    downloadViewModel.getSearchMovies(newText)
                }
                return true
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.searchMovieDownload.setOnSearchViewListener(object :
            SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
                downloadViewModel.getMovies()
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
                binding.searchMovieDownload.setMenuItem(item)
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

        downloadAdapter = DownloadAdapter(
            detailsOnClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigateAnimated(action)
            },
            deleteOnClickListener = { movie ->
                initDeleteBottomSheet(movie)
            }
        )

        binding.rvDownloads.adapter = downloadAdapter
        binding.rvDownloads.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initDeleteBottomSheet(movie: Movie) {

        val bottomSheetDialog = BottomSheetDialog(requireContext())

        val bottomSheetDeleteMovieBinding =
            BottomSheetDeleteMovieBinding.inflate(layoutInflater, null, false)

        bottomSheetDialog.setContentView(bottomSheetDeleteMovieBinding.root)

        Glide.with(binding.root.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(bottomSheetDeleteMovieBinding.ivPlay)

        bottomSheetDeleteMovieBinding.textMovie.text = movie.title
        bottomSheetDeleteMovieBinding.textDuration.text = movie.runtime?.calculateMovieTime()
        bottomSheetDeleteMovieBinding.textSize.text = movie.runtime?.toDouble()?.calculateFileSize()

        bottomSheetDeleteMovieBinding.btnRemove.setOnClickListener {
            downloadViewModel.deleteMovie(movie)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDeleteMovieBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }

    private fun isSearchResultEmpty(empty: Boolean) {
        binding.rvDownloads.isVisible = !empty
        binding.llListEmpty.isVisible = empty
    }
}