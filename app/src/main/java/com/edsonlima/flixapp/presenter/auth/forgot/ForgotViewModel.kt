package com.edsonlima.flixapp.presenter.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.edsonlima.flixapp.domain.usecase.auth.ForgotUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotUseCase
) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {

        try {

            emit(StateView.Loading())

            forgotUseCase(email)

            emit(StateView.Success(Unit))

        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }

    }

}