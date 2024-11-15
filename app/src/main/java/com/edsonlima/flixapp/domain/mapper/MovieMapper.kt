package com.edsonlima.flixapp.domain.mapper

import com.edsonlima.flixapp.data.model.CountryResponse
import com.edsonlima.flixapp.data.model.GenreResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.domain.model.Country
import com.edsonlima.flixapp.domain.model.Genre
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.presenter.model.GenrePresentation

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
        voteCount = this.voteCount,
        productionCountries = productionCountries?.map { it.toDomain() }
    )
}

fun CountryResponse.toDomain(): Country {
    return Country(
        name = this.name
    )
}

fun Genre.toPresentation(): GenrePresentation {
    return GenrePresentation(
        id = this.id,
        name = this.name,
        movies = emptyList()
    )
}
