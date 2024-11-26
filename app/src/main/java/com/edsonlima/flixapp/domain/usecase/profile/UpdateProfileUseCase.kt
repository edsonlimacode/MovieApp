package com.edsonlima.flixapp.domain.usecase.profile

import com.edsonlima.flixapp.data.repository.ProfileRepository
import com.edsonlima.flixapp.domain.model.User
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(user: User) {
        repository.update(user)
    }
}