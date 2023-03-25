package com.ilfey.shikimori.ui.history

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.models.HistoryItem
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class HistoryViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ListViewModel() {

    init {
        getHistory()
    }

    val history = MutableLiveData<List<HistoryItem>>()

    override fun onRefresh() {
        getHistory()
    }

    fun getHistory() {
        repository.history(
            id = settings.userId,
            limit = 100,
        ).enqueue {
            when(it) {
                is Result.Success -> {

                    if (it.response.isSuccessful && it.response.body() != null) {
                        history.value = it.response.body()
                    }
                }
                is Result.Failure -> {}
            }
        }
    }
}