package com.edsonlima.flixapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieByGenre(
    val id: Int?,
    val name: String?,
    val movies: List<Movie>?
): Parcelable