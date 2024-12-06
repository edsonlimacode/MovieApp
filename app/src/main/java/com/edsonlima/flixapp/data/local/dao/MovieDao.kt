package com.edsonlima.flixapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edsonlima.flixapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies WHERE userId = :userId")
    fun getAllMovies(userId: String): Flow<List<MovieEntity>>

    @Update
    suspend fun update(movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

}