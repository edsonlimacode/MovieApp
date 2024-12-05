package com.edsonlima.flixapp.presenter.auth.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.domain.usecase.auth.LogoutUseCase
import com.edsonlima.flixapp.domain.usecase.profile.GetUserByIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _userState = MutableLiveData<StateView<User?>>()
    val userState: LiveData<StateView<User?>> = _userState

    fun logout() = viewModelScope.launch {
        logoutUseCase()
    }

    fun getUserById(id: String) = viewModelScope.launch {

        try {

            _userState.value = StateView.Loading()

            val user = getUserByIdUseCase(id)

            user?.let {
                _userState.value = StateView.Success(user)
            }


        } catch (ex: Exception) {
            _userState.value = StateView.Error(ex.message)
        }

    }


}