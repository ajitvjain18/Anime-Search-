package com.ajitjain.animesearch.model

data class AnimeApiResponse(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val genres: List<Genre>,
    val trailer: Trailer?,
    val images: Images?
)

data class Images(
    val jpg: ImageDetail?,
    val webp: ImageDetail?
)

data class ImageDetail(
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?
)

data class Trailer(
    val url: String?,
    val youtube_id: String?,
    val embed_url: String?
)