package com.edsonlima.flixapp.data.repository

import android.widget.Toast
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.domain.repository.IProfileRepository
import com.edsonlima.flixapp.utils.FirebaseHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val db: FirebaseFirestore,
) : IProfileRepository {

    override suspend fun update(user: User) {
        db.collection("profiles")
            .document(FirebaseHelper.getUserId())
            .set(user)
            .await()
    }

    override suspend fun getUser(id: String): User? {
        val documentSnapshot = db.collection("profiles")
            .document(FirebaseHelper.getUserId())
            .get()
            .await()

        val user = documentSnapshot.toObject(User::class.java)

        user?.let {user ->
            return user
        }

        return null
    }
}