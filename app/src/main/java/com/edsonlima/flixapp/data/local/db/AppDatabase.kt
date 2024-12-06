package com.edsonlima.flixapp.data.local.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.edsonlima.flixapp.data.local.dao.MovieDao
import com.edsonlima.flixapp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2,  exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE movies ADD COLUMN userId TEXT")
            }
        }
    }


}