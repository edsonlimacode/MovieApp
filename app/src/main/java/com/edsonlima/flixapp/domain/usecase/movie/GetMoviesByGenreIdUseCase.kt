package com.edsonlima.flixapp.domain.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.edsonlima.flixapp.data.repository.MovieRepository
import com.edsonlima.flixapp.domain.mapper.toDomain
import com.edsonlima.flixapp.domain.model.Movie
import com.edsonlima.flixapp.utils.Constants.PAGE_MAX_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesByGenreIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        genreId: Int
    ): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_MAX_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                movieRepository.getMoviesByGenreId(genreId)
            }
        ).flow.map {
            it.map { it.toDomain() }
        }
}