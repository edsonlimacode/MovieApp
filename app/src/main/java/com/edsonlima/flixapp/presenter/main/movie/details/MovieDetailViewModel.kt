package com.edsonlima.flixapp.presenter.main.movie.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.domain.local.usecase.movie.InsertMovieUseCase
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.usecase.movie.GetCreditsUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMovieByIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getCreditsUseCase: GetCreditsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> = _movieId

    fun getMovieId(movieId: Int) {
        _movieId.value = movieId
    }

    fun getCreditsByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val credits = getCreditsUseCase(movieId)

            emit(StateView.Success(credits))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun getMovieById(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movies = getMovieByIdUseCase(movieId)

            emit(StateView.Success(movies))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun insertToDatabase(movie: Movie) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            insertMovieUseCase(movie)

            emit(StateView.Success(Unit))

        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

}