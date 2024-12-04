package com.edsonlima.flixapp.presenter.main.movie.details.tabs.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.domain.usecase.movie.GetSimilarByIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val getSimilarByIdUseCase: GetSimilarByIdUseCase
): ViewModel(){

    fun getSimilarByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val similar = getSimilarByIdUseCase(movieId)

            emit(StateView.Success(similar))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }

}