package com.ilfey.shikimori.ui.lists

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.models.AnimeItem
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

    val list = MutableLiveData<List<AnimeItem>>()

    fun searchInList(list: ListTypes, query: String) {
        loadingMutableStateFlow.value = true
        repository.animes(
            mylist = list.value,
            search = query,
        ).enqueue {
            when (it) {
                is Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        this.list.value = it.response.body()
                    }
                }
                is Failure -> {}
            }
            loadingMutableStateFlow.value = false
        }
    }

    override fun onRefresh() {}
}