package com.ajitjain.animesearch.model

data class AnimeListResponse(
    val pagination: Pagination,
    val data: List<AnimeApiResponse>
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: PaginationItems
)

data class PaginationItems(
    val count: Int,
    val total: Int,
    val per_page: Int
)