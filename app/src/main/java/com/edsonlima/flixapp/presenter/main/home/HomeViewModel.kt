package com.edsonlima.flixapp.presenter.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.domain.model.Genre
import com.edsonlima.flixapp.domain.usecase.movie.GetGenresUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.edsonlima.flixapp.domain.model.MovieByGenre
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase
) : ViewModel() {

    private val _movieList = MutableLiveData<List<MovieByGenre>>()
    val movieList: LiveData<List<MovieByGenre>> = _movieList

    private val _homeState = MutableLiveData<StateView<Unit?>>()
    val homeState: LiveData<StateView<Unit?>> = _homeState

     fun getGenres() = viewModelScope.launch {

        try {

            val genres = getGenresUseCase()
            getMoviesByGenreId(genres)

        } catch (ex: Exception) {
            _homeState.postValue(StateView.Error(ex.message))
        }

    }

    private fun getMoviesByGenreId(genres: List<Genre>) = viewModelScope.launch {

        try {

            val moviesByGenre = mutableListOf<MovieByGenre>()

            genres.forEach {

                val movies = getMoviesByGenreIdUseCase(it.id!!)
                val moviesByGenreList = MovieByGenre(
                    id = it.id,
                    name = it.name,
                    movies = movies
                )

                moviesByGenre.add(moviesByGenreList)

                if (moviesByGenre.size == genres.size) {
                    _movieList.value = moviesByGenre
                    _homeState.value = StateView.Success(Unit)
                }
            }

        } catch (ex: Exception) {
            _homeState.value = StateView.Error(ex.message)
        }

    }

}