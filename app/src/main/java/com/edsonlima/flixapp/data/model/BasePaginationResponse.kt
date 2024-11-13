package com.edsonlima.flixapp.data.model

import com.google.gson.annotations.SerializedName

data class BasePaginationResponse <out T>(
    val page: Int?,

    val results: T?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?

)