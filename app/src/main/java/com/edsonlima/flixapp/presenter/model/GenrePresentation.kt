package com.edsonlima.flixapp.presenter.model

import com.edsonlima.flixapp.domain.model.Movie

data class GenrePresentation(
    val id: String?,
    val title: String?,
    val movies: List<Movie>?
)