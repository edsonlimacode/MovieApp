package com.edsonlima.flixapp.presenter.movie.movedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.bumptech.glide.Glide
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.FragmentMovieDetailsBinding
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.presenter.movie.MovieViewModel
import com.edsonlima.flixapp.presenter.movie.movedetails.tabs.CommentsFragment
import com.edsonlima.flixapp.presenter.movie.movedetails.tabs.SimilarFragment
import com.edsonlima.flixapp.presenter.movie.movedetails.tabs.TrailerFragment
import com.edsonlima.flixapp.utils.StateView
import com.edsonlima.flixapp.utils.initToolBar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private lateinit var movieCreditAdapter: MovieCreditAdapter

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.getMovieId(args.movieId)

        initToolBar(binding.tbMovieDetail)
        initCreditRecyclerView()
        initTabs()
        getMovieById(args.movieId)
        getCreditByMovieId(args.movieId)
    }

    private fun initCreditRecyclerView() {

        movieCreditAdapter = MovieCreditAdapter()

        binding.rvCredit.adapter = movieCreditAdapter

        binding.rvCredit.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initTabs() {



        val viewPagerAdapter = ViewPagerAdapter(requireActivity())

        binding.vpDetails.adapter = viewPagerAdapter

        viewPagerAdapter.addFragment(
            TrailerFragment(),
            R.string.textTralier
        )
        viewPagerAdapter.addFragment(
            SimilarFragment(),
            R.string.textSimilar
        )
        viewPagerAdapter.addFragment(
            CommentsFragment(),
            R.string.textComentarios
        )

        TabLayoutMediator(binding.tabDetails, binding.vpDetails) { tab, position ->
            tab.text = getString(viewPagerAdapter.getTitle(position))
        }.attach()

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

    private fun setMovie(movie: Movie) {

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.imgDetail)

        binding.textTitleDetail.text = movie.title

        binding.textVoteAverage.text = String.format("%.1f", movie.voteAverage)

        binding.textCountry.text = movie.productionCountries?.get(0)?.name

        binding.textYear.text = getYearFromDate(movie.releaseDate!!)

        binding.textGenresTitle.text = getString(R.string.text_genre_name, getGenreName(movie))

        binding.textDescription.text = movie.overview
    }

    private fun getCreditByMovieId(movieId: Int) {

        movieViewModel.getCreditsByMovieId(movieId).observe(viewLifecycleOwner) { stateView ->

            when (stateView) {
                is StateView.Loading -> {}
                is StateView.Success -> {
                    stateView.data?.let { credit ->
                        movieCreditAdapter.submitList(credit.cast)
                    }
                }

                is StateView.Error -> {
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun getGenreName(movie: Movie): String? {
        val movieName = movie.genres?.map { it.name }?.joinToString(", ")
        return movieName
    }

    private fun getYearFromDate(date: String): String {
        val data = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        return data.year.toString()
    }
}