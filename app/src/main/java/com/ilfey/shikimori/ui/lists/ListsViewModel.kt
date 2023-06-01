package com.ilfey.shikimori.ui.lists

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.ListType.*
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.models.AnimeRate
import com.ilfey.shikimori.di.network.services.AnimeService
import com.ilfey.shikimori.di.network.services.UserRateService
import com.ilfey.shikimori.di.network.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListsViewModel(
    private val settings: AppSettings,
    private val animeService: AnimeService,
    private val userService: UserService,
    private val userRateService: UserRateService,
) : ListViewModel() {

    private var lastStatus: ListType = settings.list ?: PLANNED
    private var lastIndex = 0

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val searchList = MutableLiveData<List<AnimeItem>>()
    val planned = MutableLiveData<List<AnimeRate>>()
    val watching = MutableLiveData<List<AnimeRate>>()
    val completed = MutableLiveData<List<AnimeRate>>()
    val rewatching = MutableLiveData<List<AnimeRate>>()
    val on_hold = MutableLiveData<List<AnimeRate>>()
    val dropped = MutableLiveData<List<AnimeRate>>()
    val moveAnime = MutableLiveData<MoveAnime?>()

    private fun listByType(type: ListType) = when (type) {
        PLANNED -> planned
        WATCHING -> watching
        REWATCHING -> rewatching
        COMPLETED -> completed
        ON_HOLD -> on_hold
        DROPPED -> dropped
    }

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

        val list = listByType(status)

        if (refresh || list.value == null) {
            Log.d(TAG, "getList: update $status")
            userService.animeRates(
                userId = settings.userId,
                status = status,
                onSuccess = {
                    list.value = it
                    loadingMutableStateFlow.value = false
                },
                onFailure = this::onListFailure,
            )
            return
        }

        Log.d(TAG, "getList: get $status from cache")

        loadingMutableStateFlow.value = false
    }

    private fun onListFailure(tr: Throwable) {}

    fun setEpisodes(id: Long, eps: Int) {
        Log.d(TAG, "setEpisodes: $eps")
        userRateService.updateUserRate(
            id = id,
            reqBody = PatchUserRate.newInstance(
                episodes = eps,
                status = null,
            ),
        )
    }

    fun setList(
        id: Long,
        rate: AnimeRate,
        fromList: ListType,
        toList: ListType,
        cancel: Boolean = false
    ) {
        Log.d(TAG, "setList: set rate")

        userRateService.updateUserRate(
            id = id,
            reqBody = PatchUserRate.newInstance(
                status = toList,
            ),
            onSuccess = {
                if (!cancel) {
                    moveAnime.value = MoveAnime(
                        animeRate = rate,
                        fromList = fromList,
                        toList = toList,
                    )

                    val list = listByType(fromList)

                    val index = list.value?.indexOfFirst { it.id == id }
                    if (index != null && index != -1) {
                        lastIndex = index
                        val mutableList = list.value?.toMutableList()
                        mutableList?.removeAt(index)
                        list.value = mutableList
                    }

                    Log.d(TAG, "setList: update $toList")
                    userService.animeRates(
                        userId = settings.userId,
                        status = toList,
                        onSuccess = {
                            listByType(toList).value = it
                        },
                        onFailure = this::onListFailure,
                    )

                    Log.d(TAG, "setList: move ${rate.anime.id} from $fromList to $toList")
                } else {
                    val list = listByType(toList)
                    val mutableList = list.value?.toMutableList()
                    mutableList?.add(lastIndex, rate)
                    list.value = mutableList

                    Log.d(TAG, "setList: update $fromList")
                    userService.animeRates(
                        userId = settings.userId,
                        status = fromList,
                        onSuccess = {
                            listByType(fromList).value = it
                        },
                        onFailure = this::onListFailure,
                    )

                    moveAnime.value = null

                    Log.d(TAG, "setList: cancel move ${rate.anime.id} from $fromList to $toList")
                }
            },
//            TODO: handle error
        )
    }

    override fun onRefresh() {
        getList(lastStatus, true)
    }

    data class MoveAnime(
        val animeRate: AnimeRate,
        val fromList: ListType,
        val toList: ListType,
    )

    companion object {
        private const val TAG = "[ListsViewModel]"
    }
}