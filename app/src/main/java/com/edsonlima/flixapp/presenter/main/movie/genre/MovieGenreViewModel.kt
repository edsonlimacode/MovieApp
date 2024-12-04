package com.edsonlima.flixapp.presenter.main.movie.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.usecase.movie.GetMovieByIdUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdPaginationUseCase
import com.edsonlima.flixapp.domain.usecase.movie.SearchMoviesByNameUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMoviesByGenreIdPaginationUseCase: GetMoviesByGenreIdPaginationUseCase,
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movieList = _movieList.asStateFlow().cachedIn(viewModelScope)

    private var currentGenreId: Int? = null

    fun getMoviesByGenreId(genreId: Int, isSearch: Boolean = false) = viewModelScope.launch {

        if (genreId != currentGenreId || isSearch) {
            currentGenreId = genreId
            getMoviesByGenreIdPaginationUseCase(genreId).collect {
                _movieList.value = it
            }
        }
    }

    fun searchMoviesByName(query: String): Flow<PagingData<Movie>> {
        return searchMoviesByNameUseCase(query)
    }


}