package com.edsonlima.flixapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edsonlima.flixapp.utils.FirebaseHelper

@Entity("movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    var userId: String? = null,
    val title: String?,
    val poster: String?,
    val duration: Int?,
    val createdAt: Long?
) {
    init {
        userId = FirebaseHelper.getUserId()
    }
}
