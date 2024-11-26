package com.edsonlima.flixapp.domain.repository

import androidx.paging.PagingSource
import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.data.model.MovieReviewResponse

interface IMovieRepository {

    suspend fun getGenres(): GenresResponse

    fun getMoviesByGenreIdPagination(
        genreId: Int
    ): PagingSource<Int, MovieResponse>

    suspend fun getMoviesByGenreId(
        genreId: Int
    ): List<MovieResponse>

    fun searchMoviesByName(
        query: String
    ): PagingSource<Int, MovieResponse>

    suspend fun getMovieById(
        movieId: Int
    ): MovieResponse

    suspend fun getCreditsByMovieId(
        movieId: Int
    ): CreditResponse

    suspend fun getSimilarByMovieId(
        movieId: Int
    ): List<MovieResponse>

    suspend fun getCommentsByMovieId(
        movieId: Int
    ): List<MovieReviewResponse>
}