package com.ilfey.shikimori.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.services.AnimeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
    private val animeService: AnimeService,
): ViewModel() {

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val animes = MutableLiveData<List<AnimeItem>>()

    fun search(query: String?) {
        loadingMutableStateFlow.value = true
        animeService.animes(
            search = query,
            onSuccess = this::onSearchSuccess,
            onFailure = this::onSearchFailure,
        )
    }

    private fun onSearchSuccess(res: List<AnimeItem>) {
        animes.value = res
        loadingMutableStateFlow.value = false
    }

    private fun onSearchFailure(tr: Throwable) {
        Log.e(TAG, "onSearchFailure: ", tr)
    }

    companion object {
        private const val TAG = "[SearchViewModel]"
    }
}