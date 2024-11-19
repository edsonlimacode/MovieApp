package com.edsonlima.flixapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AuthorDetailsResponse(

    @SerializedName("avatar_path")
    val avatarPath: String?,

    val name: String?,

    val rating: Int?,

    val username: String?
)