package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Credit
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, apiKey: String, langauge: String): Credit {
        return movieRepository.getCreditsByMovieId(movieId, apiKey, langauge).toDomain()
    }
}