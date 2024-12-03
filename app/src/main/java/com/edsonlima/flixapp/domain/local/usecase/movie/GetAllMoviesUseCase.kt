package com.edsonlima.flixapp.domain.local.usecase.movie

import com.edsonlima.flixapp.data.local.repository.MovieLocalRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getAllMovies().map { movieEntityList ->
            movieEntityList.map { it.toDomain() }
        }
    }

}