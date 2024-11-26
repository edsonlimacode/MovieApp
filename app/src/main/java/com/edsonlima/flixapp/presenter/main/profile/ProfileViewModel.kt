package com.edsonlima.flixapp.presenter.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.domain.usecase.profile.GetUserByIdUseCase
import com.edsonlima.flixapp.domain.usecase.profile.UpdateProfileUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _userState = MutableLiveData<StateView<User?>>()
    val userState: LiveData<StateView<User?>> = _userState

    private val _profileState = MutableLiveData<StateView<Unit>>()
    val loadingState: LiveData<StateView<Unit>> = _profileState

    fun update(user: User) = viewModelScope.launch {

        _profileState.value = StateView.Loading()

        try {

            updateProfileUseCase(user)

            _profileState.value = StateView.Success(Unit)

        } catch (ex: Exception) {
            _profileState.value = StateView.Error(ex.message)
        }

    }

    fun getUserById(id: String) = viewModelScope.launch {

        try {
            _userState.value = StateView.Loading()

            val user = getUserByIdUseCase(id)

            _userState.value = StateView.Success(user)

        } catch (ex: Exception) {
            _userState.value = StateView.Error(ex.message)
        }

    }

}