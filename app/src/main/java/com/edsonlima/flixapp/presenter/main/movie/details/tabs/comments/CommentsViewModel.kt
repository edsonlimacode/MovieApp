package com.edsonlima.flixapp.presenter.main.movie.details.tabs.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.domain.usecase.movie.GetCommentsUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
): ViewModel()  {

    fun getCommentsByMovieId(movieId: Int) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val comments = getCommentsUseCase(movieId)

            emit(StateView.Success(comments))
        } catch (ex: HttpException) {
            emit(StateView.Error(ex.message))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }


}