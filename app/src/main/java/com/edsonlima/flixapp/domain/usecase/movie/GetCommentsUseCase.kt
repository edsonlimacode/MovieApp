package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Credit
import com.edsonlima.flixapp.domain.model.MovieReview
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, apiKey: String, langauge: String): List<MovieReview> {
        return movieRepository.getCommentsByMovieId(movieId, apiKey, langauge).map { it.toDomain() }
    }
}