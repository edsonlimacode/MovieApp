package com.edsonlima.flixapp.presenter.main.search

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.usecase.movie.SearchMoviesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase
) : ViewModel() {

     fun searchMoviesByName(query: String): Flow<PagingData<Movie>> {
        return searchMoviesByNameUseCase(query)
    }
}