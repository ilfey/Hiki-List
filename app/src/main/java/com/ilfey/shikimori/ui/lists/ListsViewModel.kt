package com.ilfey.shikimori.ui.lists

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.enums.ListTypes.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListsViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ListViewModel() {

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val searchList = MutableLiveData<List<AnimeItem>>()
    val list = MutableLiveData<List<AnimeRate>>()

    val lists = mutableMapOf<ListTypes, List<AnimeRate>?>(
        PLANNED to null,
        WATCHING to null,
        COMPLETED to null,
        REWATCHING to null,
        ON_HOLD to null,
        DROPPED to null,
    )

    fun searchInList(list: ListTypes, query: String) {
        loadingMutableStateFlow.value = true
        repository.animes(
            mylist = list.value,
            search = query,
            censored = !settings.isNsfwEnable,
        ).enqueue {
            when (it) {
                is Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        this.searchList.value = it.response.body()
                    }
                }
                is Failure -> {}
            }
            loadingMutableStateFlow.value = false
        }
    }

    private var lastStatus: ListTypes = settings.list ?: PLANNED

    fun getList(status: ListTypes, refresh: Boolean = false) {
        loadingMutableStateFlow.value = true
        lastStatus = status

        if (refresh || lists[status] == null) {
            repository.anime_rates(
                id = settings.userId,
                status = status.value,
                censored = !settings.isNsfwEnable,
            ).enqueue {
                when (it) {
                    is Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            list.value = it.response.body()
                            lists[status] = it.response.body()
                        }
                    }
                    is Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }

        list.value = lists[status]

        loadingMutableStateFlow.value = false
    }

    override fun onRefresh() {
        getList(lastStatus, true)
    }
}