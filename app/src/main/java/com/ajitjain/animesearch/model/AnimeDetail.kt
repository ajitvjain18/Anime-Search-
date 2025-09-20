package com.ajitjain.animesearch.model

data class AnimeDetail(
    val malId: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<Genre>,
    val trailerUrl: String?,
    val imageUrl: String,
    val mainCast: List<String>?
)


