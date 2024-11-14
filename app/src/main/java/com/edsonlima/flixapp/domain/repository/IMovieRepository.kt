package com.edsonlima.flixapp.domain.repository

import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import retrofit2.http.Query

interface IMovieRepository {

    suspend fun getGenres(
        apiKey: String,
        language: String
    ): GenresResponse

    suspend fun getMoviesByGenreId(
        apiKey: String,
        language: String,
        genreId: Int,
    ): List<MovieResponse>

    suspend fun searchMoviesByName(
        apiKey: String,
        language: String,
        query: String?
    ): List<MovieResponse>

    suspend fun getMovieById(
        apiKey: String,
        language: String,
        movieId: Int
    ): MovieResponse
}