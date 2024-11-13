package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import javax.inject.Inject

class GetMoviesByGenreIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, langauge: String, genreId: Int): List<Movie> {
        return movieRepository.getMoviesByGenreId(apiKey, langauge, genreId).map { it.toDomain() }
    }
}