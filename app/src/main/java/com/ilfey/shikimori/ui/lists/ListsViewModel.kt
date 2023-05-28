package com.ilfey.shikimori.ui.lists

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.di.network.services.AnimeService
import com.ilfey.shikimori.di.network.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListsViewModel(
    private val settings: AppSettings,
    private val animeService: AnimeService,
    private val userService: UserService,
) : ListViewModel() {

    private var lastStatus: ListType = settings.list ?: PLANNED

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val searchList = MutableLiveData<List<AnimeItem>>()
    val list = MutableLiveData<List<AnimeRate>>()

    val lists = mutableMapOf<ListType, List<AnimeRate>?>(
        PLANNED to null,
        WATCHING to null,
        COMPLETED to null,
        REWATCHING to null,
        ON_HOLD to null,
        DROPPED to null,
    )

    fun searchInList(list: ListType, query: String) {
        loadingMutableStateFlow.value = true
        animeService.animes(
            list = list,
            search = query,
            onSuccess = this::onSearchSuccess,
            onFailure = this::onSearchFailure
        )
    }

    private fun onSearchSuccess(list: List<AnimeItem>) {
        this.searchList.value = list
        loadingMutableStateFlow.value = false
    }

    private fun onSearchFailure(tr: Throwable) {}

    fun getList(status: ListType, refresh: Boolean = false) {
        loadingMutableStateFlow.value = true
        lastStatus = status

        if (refresh || lists[status] == null) {
            userService.animeRates(
                userId = settings.userId,
                status = status,
                onSuccess = {
                    this.list.value = it
                    this.lists[status] = it
                    loadingMutableStateFlow.value = false
                },
                onFailure = this::onListFailure,
            )
            return
        }

        list.value = lists[status]

        loadingMutableStateFlow.value = false
    }

    private fun onListFailure(tr: Throwable) {}

    override fun onRefresh() {
        getList(lastStatus, true)
    }
}