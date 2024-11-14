package com.edsonlima.flixapp.data.api

import com.edsonlima.flixapp.data.model.BasePaginationResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_genres") genreId: Int,
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("search/movie")
    suspend fun searchMoviesByName(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String?,
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("movie/{movei_id}")
    suspend fun getMovieById(
        @Path("movei_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): MovieResponse

}