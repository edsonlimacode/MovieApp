package com.edsonlima.flixapp.domain.usecase.auth

import com.edsonlima.flixapp.data.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }

}