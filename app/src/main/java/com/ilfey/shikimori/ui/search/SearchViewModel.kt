package com.ilfey.shikimori.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
): ViewModel() {

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val animes = MutableLiveData<List<AnimeItem>>()

    fun search(query: String?) {
        loadingMutableStateFlow.value = true
        repository.animes(
            search = query
        ).enqueue {
            when(it) {
                is Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        animes.value = it.response.body()
                    }
                }
                is Failure -> {}
            }
            loadingMutableStateFlow.value = false
        }
    }
}