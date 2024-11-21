package com.edsonlima.flixapp.presenter.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.BuildConfig
import com.edsonlima.flixapp.domain.local.usecase.InsertMovieUseCase
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.usecase.movie.GetCommentsUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetCreditsUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMovieByIdUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.edsonlima.flixapp.domain.usecase.movie.GetSimilarByIdUseCase
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
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getSimilarByIdUseCase: GetSimilarByIdUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int> = _movieId

    fun getMovieId(movieId: Int) {
        _movieId.value = movieId
    }

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

    fun getCreditsByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val credits = getCreditsUseCase(movieId, BuildConfig.API_KEY, "pt-BR")

            emit(StateView.Success(credits))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

    fun getSimilarByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val similars = getSimilarByIdUseCase(BuildConfig.API_KEY, "pt-BR", movieId)

            emit(StateView.Success(similars))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }


    fun getCommentsByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val comments = getCommentsUseCase(movieId, BuildConfig.API_KEY, "en-US")

            emit(StateView.Success(comments))
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