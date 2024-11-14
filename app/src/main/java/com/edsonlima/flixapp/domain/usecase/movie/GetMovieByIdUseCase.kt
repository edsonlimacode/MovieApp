package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, langauge: String, movieId: Int): Movie {
        return movieRepository.getMovieById(apiKey, langauge, movieId).toDomain()
    }
}