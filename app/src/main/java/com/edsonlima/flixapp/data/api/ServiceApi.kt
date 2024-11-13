package com.edsonlima.flixapp.data.api

import com.edsonlima.flixapp.data.model.BasePaginationResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenresResponse

    @GET("genre/discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_genres") genreId: Int,
    ): BasePaginationResponse<List<MovieResponse>>

}