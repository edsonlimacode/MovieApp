package com.edsonlima.flixapp.domain.usecase.auth

import com.edsonlima.flixapp.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String) {
        authRepository.register(email, password)
    }

}