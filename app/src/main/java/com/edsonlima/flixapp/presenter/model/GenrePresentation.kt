package com.edsonlima.flixapp.presenter.model

import com.edsonlima.flixapp.domain.model.Movie

data class GenrePresentation(
    val id: Int?,
    val name: String?,
    val movies: List<Movie>?
)