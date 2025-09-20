package com.ajitjain.animesearch.model

data class AnimeItem(
    val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val imageUrl: String
)
