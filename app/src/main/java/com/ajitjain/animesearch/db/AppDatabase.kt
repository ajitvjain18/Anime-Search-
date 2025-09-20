package com.ajitjain.animesearch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ajitjain.animesearch.model.CachedAnimeDetail
import com.ajitjain.animesearch.model.Converters

@Database(entities = [CachedAnimeDetail::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao() : AnimeDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anime_cache_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}