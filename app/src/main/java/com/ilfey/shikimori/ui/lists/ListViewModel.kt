package com.ilfey.shikimori.ui.lists

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.utils.RetrofitEnqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
): com.ilfey.shikimori.base.ListViewModel() {


    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val list = MutableLiveData<List<AnimeRate>>()

    private var lastStatus: ListTypes = settings.list ?: ListTypes.PLANNED

    fun getList(status: ListTypes) {
        loadingMutableStateFlow.value = true
        lastStatus = status
        repository.anime_rates(
            id = settings.userId,
            status = status.value,
            censored = !settings.isNsfwEnable,
        ).enqueue {
            when (it) {
                is RetrofitEnqueue.Companion.Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        list.value = it.response.body()
                    }
                }
                is RetrofitEnqueue.Companion.Result.Failure -> {}
            }
            loadingMutableStateFlow.value = false
        }
    }

    override fun onRefresh() {
        getList(lastStatus)
    }
}