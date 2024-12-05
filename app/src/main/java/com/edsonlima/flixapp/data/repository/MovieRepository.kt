package com.edsonlima.flixapp.data.repository

import androidx.paging.PagingSource
import com.edsonlima.flixapp.data.api.ServiceApi
import com.edsonlima.flixapp.data.model.CreditResponse
import com.edsonlima.flixapp.data.model.GenresResponse
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.data.model.MovieReviewResponse
import com.edsonlima.flixapp.data.model.TrailerByMovieResponse
import com.edsonlima.flixapp.data.model.TrailerResponse
import com.edsonlima.flixapp.data.paging.MoviePagingSource
import com.edsonlima.flixapp.data.paging.MovieSearchPagingSource
import com.edsonlima.flixapp.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: ServiceApi
) : IMovieRepository {
    override suspend fun getGenres(): GenresResponse {
        return service.getGenres()
    }

    override fun getMoviesByGenreIdPagination(
        genreId: Int
    ): PagingSource<Int, MovieResponse> {
        return MoviePagingSource(service, genreId)
    }

    override suspend fun getMoviesByGenreId(genreId: Int): List<MovieResponse> {
        return service.getMoviesByGenreId(genreId).results ?: emptyList()
    }

    override fun searchMoviesByName(
        query: String
    ): PagingSource<Int, MovieResponse> {
        return MovieSearchPagingSource(service, query)
    }

    override suspend fun getMovieById(
        movieId: Int
    ): MovieResponse {
        return service.getMovieById(movieId)
    }

    override suspend fun getCreditsByMovieId(
        movieId: Int
    ): CreditResponse {
        return service.getCreditsByMovieId(movieId)
    }

    override suspend fun getSimilarByMovieId(
        movieId: Int
    ): List<MovieResponse> {
        return service.getSimilarByMovieId(movieId).results ?: emptyList()
    }

    override suspend fun getCommentsByMovieId(
        movieId: Int
    ): List<MovieReviewResponse> {
        return service.getCommentsByMovieId(movieId).results ?: emptyList()
    }

    override suspend fun getTrailersByMovieId(movieId: Int): List<TrailerResponse> {
        return service.getTrailersByMovieId(movieId).results ?: emptyList()
    }
}