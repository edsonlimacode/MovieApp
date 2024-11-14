package com.edsonlima.flixapp.presenter.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.BuildConfig
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase
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

}