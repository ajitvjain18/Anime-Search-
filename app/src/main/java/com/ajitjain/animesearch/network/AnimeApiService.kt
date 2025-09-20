package com.ajitjain.animesearch.network

import com.ajitjain.animesearch.model.AnimeDetail
import com.ajitjain.animesearch.model.AnimeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ): Response<AnimeDetail>

    @GET("top/anime")
    suspend fun getAll(@Query("page") page: Int): Response<AnimeListResponse>

}