package com.edsonlima.flixapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edsonlima.flixapp.data.local.dao.MovieDao
import com.edsonlima.flixapp.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(
        @ApplicationContext context: Context
    ): RoomDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "movie_database"
    ).build()

    @Singleton
    @Provides
    fun providesMovieDao(
        database: AppDatabase
    ): MovieDao = database.userDao()

}