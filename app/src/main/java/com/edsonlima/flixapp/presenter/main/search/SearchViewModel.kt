package com.edsonlima.flixapp.presenter.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.BuildConfig
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.usecase.movie.SearchMoviesByNameUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase
) : ViewModel() {

    private val _movieList = MutableLiveData<StateView<List<Movie>>>()
    val movieList: LiveData<StateView<List<Movie>>> = _movieList

    /*private val _searchState = MutableLiveData<StateView<Unit>>()
    val searchState: LiveData<StateView<Unit>> = _searchState*/

    /*fun searchMoviesByName(query: String?) = viewModelScope.launch {

        try {
            _searchState.value = StateView.Loading()

            val movies = searchMoviesByNameUseCase(BuildConfig.API_KEY, "pt-BR", query)

            _movieList.value = movies

            _searchState.value = StateView.Success(Unit)
        } catch (ex: HttpException) {
            _searchState.value = StateView.Error(ex.message)
        } catch (ex: Exception) {
            _searchState.value = StateView.Error(ex.message)
        }
    }*/

    fun searchMoviesByName(query: String?) = viewModelScope.launch {

        try {
            _movieList.value = StateView.Loading()

            val movies = searchMoviesByNameUseCase(BuildConfig.API_KEY, "pt-BR", query)

            _movieList.value = StateView.Success(movies)

        } catch (ex: HttpException) {
            _movieList.value = StateView.Error(ex.message)
        } catch (ex: Exception) {
            _movieList.value = StateView.Error(ex.message)
        }
    }

}