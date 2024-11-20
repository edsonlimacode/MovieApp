package com.edsonlima.flixapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val title: String?,
    val poster: String?,
    val duration: Int?,
    val createdAt: Long?
)
