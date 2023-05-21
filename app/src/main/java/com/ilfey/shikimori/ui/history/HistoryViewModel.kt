package com.ilfey.shikimori.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ListViewModel() {

    private var lastUsername: String? = null
    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val history = MutableLiveData<List<HistoryItem>>()

    override fun onRefresh() {
        lastUsername?.let { getHistory(it, true) }.also {
            Log.e(TAG, "onRefresh: lastUsername can not be null")
        }
    }

    fun getHistory(id: String, refresh: Boolean = false) {
        lastUsername = id
        loadingMutableStateFlow.value = true
        if (refresh || history.value == null) {
            Log.d(TAG, "getHistory: from network")
            repository.history(
                id = id,
            ).enqueue {
                when(it) {
                    is Result.Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            history.value = it.response.body()
                        }
                    }
                    is Result.Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }

        Log.d(TAG, "getHistory: from cache")
        loadingMutableStateFlow.value = false
    }

    companion object {
        private const val TAG = "[HistoryViewModel]"
    }
}