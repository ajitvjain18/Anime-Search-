package com.ajitjain.animesearch.model

// API -> Domain
fun AnimeApiResponse.toAnimeDetail(): AnimeDetail {
    return AnimeDetail(
        malId = mal_id,
        title = title,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        genres = genres,
        trailerUrl = trailer?.url,
        imageUrl = images?.jpg?.large_image_url ?: "", // JPG only
        mainCast = emptyList()
    )
}

// Domain -> Cached
fun AnimeDetail.toCached(): CachedAnimeDetail {
    return CachedAnimeDetail(
        malId = malId,
        title = title,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        genres = genres.map { it.name },
        trailerUrl = trailerUrl,
        imageUrl = imageUrl,
        mainCast = mainCast ?: emptyList()
    )
}

// Cached -> Domain
fun CachedAnimeDetail.toDomain(): AnimeDetail {
    return AnimeDetail(
        malId = malId,
        title = title,
        synopsis = synopsis,
        episodes = episodes,
        score = score,
        genres = genres.map { Genre(0, "", it, "") },
        trailerUrl = trailerUrl,
        imageUrl = imageUrl ?: "",
        mainCast = mainCast ?: emptyList()
    )
}

// List Mapping
fun AnimeListResponse.toDomain(): List<AnimeDetail> {
    return data.map { it.toAnimeDetail() }
}