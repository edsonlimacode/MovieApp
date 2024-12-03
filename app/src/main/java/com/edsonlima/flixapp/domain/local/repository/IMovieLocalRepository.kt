package com.edsonlima.flixapp.domain.local.repository

import com.edsonlima.flixapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieLocalRepository {

    suspend fun insert(movieEntity: MovieEntity)
    fun getAllMovies(): Flow<List<MovieEntity>>
    suspend fun update(movieEntity: MovieEntity)
    suspend fun delete(movieEntity: MovieEntity)
    suspend fun saveImage(image: String?)

}