package com.edsonlima.flixapp.domain.repository

import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse

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

    suspend fun getCreditsByMovieId(
        movieId: Int,
        apiKey: String,
        language: String,
    ): CreditResponse

    suspend fun getSimilarByMovieId(
        movieId: Int,
        apiKey: String,
        language: String,
    ): List<MovieResponse>
}