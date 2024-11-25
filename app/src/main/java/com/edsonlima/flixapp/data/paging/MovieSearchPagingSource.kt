package com.edsonlima.flixapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edsonlima.flixapp.data.api.ServiceApi
import com.edsonlima.flixapp.data.model.MovieResponse
import com.edsonlima.flixapp.utils.Constants.PAGE_INDEX

class MovieSearchPagingSource(
    private val serviceApi: ServiceApi,
    private val query: String
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieResponse> {
        try {

            val page = params.key ?: PAGE_INDEX
            val response = serviceApi.searchMoviesByName(
                query = query,
                page = page
            ).results

            return LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (page == PAGE_INDEX) null else page - 1,
                nextKey = if (response?.isEmpty() == true) null else page + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}