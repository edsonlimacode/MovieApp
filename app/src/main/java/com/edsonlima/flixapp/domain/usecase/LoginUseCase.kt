package com.edsonlima.flixapp.domain.usecase

import com.edsonlima.flixapp.data.repository.AuthRepository

class LoginUseCase constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) {
        authRepository.login(email, password)
    }

}