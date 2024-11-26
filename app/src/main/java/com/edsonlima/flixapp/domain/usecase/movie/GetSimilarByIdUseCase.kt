package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import javax.inject.Inject

class GetSimilarByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): List<Movie> {
        return movieRepository.getSimilarByMovieId( movieId).map { it.toDomain() }.filter { it.posterPath != null }
    }
}