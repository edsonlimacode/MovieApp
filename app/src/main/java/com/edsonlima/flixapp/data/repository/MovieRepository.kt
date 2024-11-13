package com.edsonlima.flixapp.data.repository

import com.edsonlima.flixapp.data.api.ServiceApi
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: ServiceApi
) : IMovieRepository {
    override suspend fun getGenres(apiKey: String, language: String): GenresResponse {
        return service.getGenres(apiKey, language)
    }

    override suspend fun getMoviesByGenreId(
        apiKey: String,
        language: String,
        genreId: Int
    ): List<MovieResponse> {
        return service.getMoviesByGenreId(apiKey, language, genreId).results ?: emptyList()
    }
}