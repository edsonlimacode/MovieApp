package com.edsonlima.flixapp.domain.repository

import com.edsonlima.flixapp.domain.model.User

interface IProfileRepository {
    suspend fun update(user: User)
    suspend fun getUser(id: String): User?
}