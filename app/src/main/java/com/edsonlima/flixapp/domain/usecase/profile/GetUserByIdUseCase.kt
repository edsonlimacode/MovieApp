package com.edsonlima.flixapp.domain.usecase.profile

import com.edsonlima.flixapp.data.repository.ProfileRepository
import com.edsonlima.flixapp.domain.model.User
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(id: String): User? {
        return repository.getUser(id)
    }

}