package com.edsonlima.flixapp.data.api

import com.edsonlima.flixapp.data.model.BasePaginationResponse
import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.data.model.MovieReviewResponse
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

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsByMovieId(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): CreditResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarByMovieId(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("movie/{movie_id}/reviews")
    suspend fun getCommentsByMovieId(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): BasePaginationResponse<List<MovieReviewResponse>>

}