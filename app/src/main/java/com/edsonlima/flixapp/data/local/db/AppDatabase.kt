package com.edsonlima.flixapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edsonlima.flixapp.data.local.dao.MovieDao
import com.edsonlima.flixapp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao }