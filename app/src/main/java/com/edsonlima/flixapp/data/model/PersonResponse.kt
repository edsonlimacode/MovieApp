package com.edsonlima.flixapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PersonResponse(

    val adult: Boolean?,

    @SerializedName("cast_id")
    val castId: Int?,

    val character: String?,

    @SerializedName("credit_id")
    val creditId: String?,

    val gender: Int?,

    val id: Int?,

    @SerializedName("known_for_department")
    val knownForDepartment: String?,

    val name: String?,

    val order: Int?,

    @SerializedName("original_name")
    val originalName: String?,

    val popularity: Double?,

    @SerializedName("profile_path")
    val profilePath: String?
)