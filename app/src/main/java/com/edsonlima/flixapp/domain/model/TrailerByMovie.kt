package com.edsonlima.flixapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrailerByMovie(
    val results: List<Trailer>? = null
) : Parcelable