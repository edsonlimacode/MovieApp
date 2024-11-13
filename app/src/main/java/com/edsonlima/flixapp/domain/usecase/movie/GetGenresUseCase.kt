package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Genre
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, langauge: String): List<Genre> {
        return movieRepository.getGenres(apiKey, langauge).genres.map { it.toDomain() }
    }
}