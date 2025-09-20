package com.ajitjain.animesearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class CachedAnimeDetail(
    @PrimaryKey val malId: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<String>,
    val trailerUrl: String?,
    val imageUrl: String?,
    val mainCast: List<String>?
)
