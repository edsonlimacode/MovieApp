package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import javax.inject.Inject

class SearchMoviesByNameUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, language: String, query: String?): List<Movie> {
        return movieRepository.searchMoviesByName(apiKey, language, query)
            .filter { it.posterPath != null }.map { it.toDomain() }
    }
}