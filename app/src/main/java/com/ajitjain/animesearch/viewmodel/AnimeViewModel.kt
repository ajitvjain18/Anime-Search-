package com.ajitjain.animesearch.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajitjain.animesearch.db.AppDatabase
import com.ajitjain.animesearch.model.AnimeDetail
import com.ajitjain.animesearch.network.AnimeRepository
import com.ajitjain.animesearch.network.NetworkService
import kotlinx.coroutines.launch

class AnimeViewModel(application: Application) : ViewModel() {
    val api = NetworkService.api
    private val dao = AppDatabase.getInstance(application).animeDao()
    private val repository = AnimeRepository(api, dao)

    private val _animes = MutableLiveData<List<AnimeDetail>>()
    val animes: LiveData<List<AnimeDetail>> get() = _animes

    private val _animeDetail = MutableLiveData<AnimeDetail>()
    val animeDetail: LiveData<AnimeDetail> get() = _animeDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var allAnimes: List<AnimeDetail> = emptyList()

    private var currentPage = 1
    private var isLoadingMore = false


    fun fetchAll(reset: Boolean = false) {
        if (reset) {
            currentPage = 1
            _animes.value = emptyList()
        }

        if (isLoadingMore) return
        isLoadingMore = true
        _isLoading.value = true

        viewModelScope.launch {
            when (val result = repository.getAllAnime(currentPage)) {
                is com.ajitjain.animesearch.network.Result.Success -> {
                    val currentList = _animes.value ?: emptyList()
                    _animes.value = currentList + result.data
                    currentPage++
                    _isLoading.value = false
                    isLoadingMore = false
                }
                is com.ajitjain.animesearch.network.Result.Error -> {
                    _error.value = result.message
                    _isLoading.value = false
                    isLoadingMore = false
                }
            }
        }
    }

    fun fetchAnimeDetail(id : Int) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getAnimeById(id)) {
                is com.ajitjain.animesearch.network.Result.Success -> {
                    _animeDetail.value = result.data
                    _isLoading.value = false
                }
                is com.ajitjain.animesearch.network.Result.Error -> {
                    _error.value = result.message
                    _isLoading.value = false
                }
            }
        }
    }
}