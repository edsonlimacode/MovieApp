package com.edsonlima.flixapp.data.api

import com.edsonlima.flixapp.data.model.BasePaginationResponse
import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.data.model.MovieReviewResponse
import com.edsonlima.flixapp.data.model.TrailerByMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("with_genres") genreId: Int
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("search/movie")
    suspend fun searchMoviesByName(
        @Query("query") query: String?,
        @Query("page") page: Int
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("movie/{movei_id}")
    suspend fun getMovieById(
        @Path("movei_id") movieId: Int,
    ): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsByMovieId(
        @Path("movie_id") movieId: Int,
    ): CreditResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarByMovieId(
        @Path("movie_id") movieId: Int,
    ): BasePaginationResponse<List<MovieResponse>>

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailersByMovieId(
        @Path("movie_id") movieId: Int,
    ): TrailerByMovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getCommentsByMovieId(
        @Path("movie_id") movieId: Int,
    ): BasePaginationResponse<List<MovieReviewResponse>>
}