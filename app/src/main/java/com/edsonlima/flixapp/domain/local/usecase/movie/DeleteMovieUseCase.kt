package com.edsonlima.flixapp.domain.local.usecase.movie

import com.edsonlima.flixapp.data.local.repository.MovieLocalRepository
import com.edsonlima.flixapp.domain.mapper.toEntity
import com.edsonlima.flixapp.domain.model.Movie
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movie: Movie) {
        return repository.delete(movie.toEntity())
    }
}