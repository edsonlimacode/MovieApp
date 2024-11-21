package com.edsonlima.flixapp.domain.local.usecase

import com.edsonlima.flixapp.data.local.repository.MovieLocalRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.mapper.toEntity
import com.edsonlima.flixapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movie: Movie) {
        return repository.delete(movie.toEntity())
    }
}