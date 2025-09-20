package com.ajitjain.animesearch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajitjain.animesearch.model.CachedAnimeDetail

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    suspend fun getAll(): List<CachedAnimeDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<CachedAnimeDetail>)

    @Query("DELETE FROM anime")
    suspend fun clear()
}