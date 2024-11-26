package com.edsonlima.flixapp.domain.repository

interface IAuthRepository {

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun forgot(email: String)
    suspend fun logout()

}