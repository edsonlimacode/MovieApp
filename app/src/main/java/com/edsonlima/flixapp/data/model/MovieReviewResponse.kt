package com.edsonlima.flixapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieReviewResponse(
    val author: String?,

    @SerializedName("author_details")
    val authorDetailsResponse: AuthorDetailsResponse?,

    val content: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    val id: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    val url: String?
)