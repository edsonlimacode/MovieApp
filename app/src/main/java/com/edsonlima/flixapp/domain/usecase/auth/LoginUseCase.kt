package com.edsonlima.flixapp.domain.usecase.auth

import com.edsonlima.flixapp.data.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) {
        authRepository.login(email, password)
    }

}