package com.edsonlima.flixapp.domain.mapper

import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.CountryResponse
import com.edsonlima.flixapp.data.model.GenreResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.data.model.PersonResponse
import com.edsonlima.flixapp.domain.model.Credit
import com.edsonlima.flixapp.domain.model.Country
import com.edsonlima.flixapp.domain.model.Genre
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.domain.model.Person
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
        genres = this.genres?.map { it.toDomain() },
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

fun CreditResponse.toDomain(): Credit {
    return Credit(
        cast = this.cast.map { it.toDomain() }
    )
}

fun PersonResponse.toDomain(): Person {
    return Person(
        adult = this.adult,
        castId = this.castId,
        character = this.character,
        creditId = this.creditId,
        gender = this.gender,
        id = this.id,
        knownForDepartment = this.knownForDepartment,
        name = this.name,
        order = this.order,
        originalName = this.originalName,
        popularity = this.popularity,
        profilePath = this.profilePath
    )
}

fun Genre.toPresentation(): GenrePresentation {
    return GenrePresentation(
        id = this.id,
        name = this.name,
        movies = emptyList()
    )
}
