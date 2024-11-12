package com.edsonlima.flixapp.presenter.model

import com.edsonlima.flixapp.data.model.Movie

data class GenrePresentation(
    val id: String?,
    val title: String?,
    val movies: List<Movie>?
)