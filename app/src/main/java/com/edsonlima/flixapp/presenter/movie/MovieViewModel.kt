package com.edsonlima.flixapp.presenter.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.BuildConfig
import com.edsonlima.flixapp.domain.usecase.movie.GetMovieByIdUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.edsonlima.flixapp.domain.usecase.movie.SearchMoviesByNameUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase,
    private val searchMoviesByNameUseCase: SearchMoviesByNameUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    fun getMoviesByGenreId(genreId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val genres = getMoviesByGenreIdUseCase(BuildConfig.API_KEY, "pt-BR", genreId)

            emit(StateView.Success(genres))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun searchMoviesByName(query: String?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movies = searchMoviesByNameUseCase(BuildConfig.API_KEY, "pt-BR", query)

            emit(StateView.Success(movies))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun getMovieById(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movies = getMovieByIdUseCase(BuildConfig.API_KEY, "pt-BR", movieId)

            emit(StateView.Success(movies))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

}