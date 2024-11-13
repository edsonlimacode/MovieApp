package com.edsonlima.flixapp.domain.mapper

import com.edsonlima.flixapp.data.model.GenreResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.domain.model.Genre
import com.edsonlima.flixapp.domain.model.Movie

fun GenreResponse.toDomain(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun MovieResponse.toDomain(): Movie {
    return Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}