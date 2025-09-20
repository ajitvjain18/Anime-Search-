package com.ajitjain.animesearch.network

import com.ajitjain.animesearch.db.AnimeDao
import com.ajitjain.animesearch.model.AnimeDetail
import com.ajitjain.animesearch.model.toAnimeDetail
import com.ajitjain.animesearch.model.toCached
import com.ajitjain.animesearch.model.toDomain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

class AnimeRepository(
    private val api: AnimeApiService,
    private val animeDao: AnimeDao
) {
    suspend fun getAnimeById(id: Int): Result<AnimeDetail> {
        return try {
            val response = api.getAnimeDetails(id)
            if (response.isSuccessful) {
                response.body()?.let { animeDetail ->
                    animeDao.insertAll(listOf(animeDetail.toCached()))
                    Result.Success(animeDetail)
                } ?: Result.Error("Empty response body")
            } else {
                Result.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            val cached = animeDao.getAll().find { it.malId == id }
            cached?.let { Result.Success(it.toDomain()) }
                ?: Result.Error("Exception: ${e.message}. No cached data found.")
        }
    }

    suspend fun getAllAnime(page: Int): Result<List<AnimeDetail>> {
        return try {
            val response = api.getAll(page)
            if (response.isSuccessful) {
                response.body()?.let { animeResponse ->
                    val animeList = animeResponse.data.map { it.toAnimeDetail() }

                    if (page == 1) {
                        animeDao.clear()
                    }
                    animeDao.insertAll(animeList.map { it.toCached() })

                    Result.Success(animeList)
                } ?: Result.Error("Empty response body")
            } else {
                Result.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            val cachedList = animeDao.getAll()
            if (cachedList.isNotEmpty()) {
                Result.Success(cachedList.map { it.toDomain() })
            } else {
                Result.Error("Exception: ${e.message}. No cached data found.")
            }
        }
    }



}
