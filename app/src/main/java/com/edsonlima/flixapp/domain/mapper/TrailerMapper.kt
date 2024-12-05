package com.edsonlima.flixapp.domain.mapper

import com.edsonlima.flixapp.data.model.TrailerByMovieResponse
import com.edsonlima.flixapp.data.model.TrailerResponse
import com.edsonlima.flixapp.domain.model.Trailer
import com.edsonlima.flixapp.domain.model.TrailerByMovie


fun TrailerResponse.toDomain(): Trailer {
    return Trailer(
        name = this.name,
        key = this.key
    )
}

fun TrailerByMovieResponse.toDomain(): TrailerByMovie {
    return TrailerByMovie(
        results = this.results?.map { it.toDomain() }
    )
}