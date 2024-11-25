package com.edsonlima.flixapp.presenter.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.domain.mapper.toPresentation
import com.edsonlima.flixapp.domain.usecase.movie.GetGenresUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase
) : ViewModel() {

    fun getGenres() = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val genres = getGenresUseCase().map { it.toPresentation() }

            emit(StateView.Success(genres))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun getMoviesByGenreId(genreId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val genres = getMoviesByGenreIdUseCase(genreId)

            emit(StateView.Success(genres))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

}