package com.edsonlima.flixapp.data.repository

import com.edsonlima.flixapp.domain.repository.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository constructor(
    private val auth: FirebaseAuth
) : IAuthRepository {

    override suspend fun login(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun register(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun forgot(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}