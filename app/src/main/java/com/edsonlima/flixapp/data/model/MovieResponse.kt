package com.edsonlima.flixapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(

    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val genres: List<GenreResponse>?,

    val id: Int?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    val overview: String?,

    val popularity: Float?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    val title: String?,

    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Float?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    val runtime: Int?,

    @SerializedName("production_countries")
    val productionCountries: List<CountryResponse>?
)
