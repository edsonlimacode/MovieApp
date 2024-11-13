package com.edsonlima.flixapp.presenter.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentHomeAuthBinding
import com.edsonlima.flixapp.databinding.FragmentHomeBinding
import com.edsonlima.flixapp.presenter.main.home.adapter.GenreMovieAdapter
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

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
                    genreMovieAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {

        genreMovieAdapter = GenreMovieAdapter()

        binding.rvHome.adapter = genreMovieAdapter

    }

}