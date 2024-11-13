package com.edsonlima.flixapp.domain.repository

import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import retrofit2.http.Query

interface IMovieRepository {

    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenresResponse

    suspend fun getMoviesByGenreId(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_genres") genreId: Int,
    ): List<MovieResponse>
}