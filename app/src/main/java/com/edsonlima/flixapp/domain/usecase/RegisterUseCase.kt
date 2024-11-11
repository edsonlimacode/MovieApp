package com.edsonlima.flixapp.domain.usecase

import com.edsonlima.flixapp.data.repository.AuthRepository

class RegisterUseCase constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) {
        authRepository.register(email, password)
    }

}