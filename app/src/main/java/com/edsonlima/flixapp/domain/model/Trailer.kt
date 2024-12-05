package com.edsonlima.flixapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trailer(
    val name: String? = null,
    val key: String? = null,
) : Parcelable