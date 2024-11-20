package com.edsonlima.flixapp.data.local.repository

import com.edsonlima.flixapp.data.local.dao.MovieDao
import javax.inject.Inject

class MovieDbRepository @Inject constructor(
    private val movieDao: MovieDao
) {
}