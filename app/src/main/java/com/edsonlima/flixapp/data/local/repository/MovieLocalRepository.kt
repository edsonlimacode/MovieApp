package com.edsonlima.flixapp.data.local.repository

import com.edsonlima.flixapp.data.local.dao.MovieDao
import com.edsonlima.flixapp.data.local.entity.MovieEntity
import com.edsonlima.flixapp.domain.local.repository.IMovieLocalRepository
import com.edsonlima.flixapp.domain.model.User
import com.edsonlima.flixapp.utils.FirebaseHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalRepository @Inject constructor(
    private val movieDao: MovieDao
) : IMovieLocalRepository {
    override suspend fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies(FirebaseHelper.getUserId())
    }

    override suspend fun update(movieEntity: MovieEntity) {
        movieDao.update(movieEntity)
    }

    override suspend fun delete(movieEntity: MovieEntity) {
        movieDao.delete(movieEntity)
    }

    override suspend fun saveImage(image: String?) {
        TODO("Not yet implemented")
    }
}