package com.edsonlima.flixapp.presenter.main.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.domain.local.usecase.DeleteMovieUseCase
import com.edsonlima.flixapp.domain.local.usecase.GetAllMoviesUseCase
import com.edsonlima.flixapp.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val getDeleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _moviesList

    fun getMovies() = viewModelScope.launch {

        getAllMoviesUseCase().collect { movies ->
            _moviesList.value = movies
        }
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {

        getDeleteMovieUseCase(movie)

        getMovies()
    }


}