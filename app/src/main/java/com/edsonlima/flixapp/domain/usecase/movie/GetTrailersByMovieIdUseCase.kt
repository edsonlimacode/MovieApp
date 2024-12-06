package com.edsonlima.flixapp.domain.usecase.movie

import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Trailer
import javax.inject.Inject

class GetTrailersByMovieIdUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int): List<Trailer> {
        return repository.getTrailersByMovieId(movieId).map { it.toDomain() }
    }

}