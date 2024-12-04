package com.edsonlima.flixapp.presenter.main.profile

import android.widget.Toast
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

    private val _validate = MutableLiveData<Pair<Boolean, String?>>()
    val validate: LiveData<Pair<Boolean, String?>> = _validate

    private val _userState = MutableLiveData<StateView<User?>>()
    val userState: LiveData<StateView<User?>> = _userState


    fun update(user: User) = viewModelScope.launch {

        try {

            updateProfileUseCase(user)

        } catch (ex: Exception) {
            _userState.value = StateView.Error(ex.message)
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

    fun validate(
        name: String,
        lastName: String,
        genre: String,
        selectedItemPosition: Int
    ) {
        if (name.isEmpty()) {
            _validate.value = Pair(false, "Nome é obrigatório")
            return
        }

        if (lastName.isEmpty()) {
            _validate.value = Pair(false, "Sobrenome é obrigatório")
            return
        }

        if (genre.isEmpty() || selectedItemPosition == 0) {
            _validate.value = Pair(false, "Gênero é obrigatório")
            return
        }

        _validate.value = Pair(true, null)
    }

}