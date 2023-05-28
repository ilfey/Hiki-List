package com.ilfey.shikimori.ui.anime

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.bodies.PostUserRate
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.di.network.services.AnimeService
import com.ilfey.shikimori.di.network.services.UserRateService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimeViewModel(
    private val settings: AppSettings,
    private val userRateService: UserRateService,
    private val animeService: AnimeService,
) : ViewModel() {

    private var id = 0L

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val anime = MutableLiveData<Anime>()
    val userRate = MutableLiveData<UserRate>()
    val roles = MutableLiveData<List<Role>>()

    fun getAnime(id: Long, refresh: Boolean = false) {
        this.id = id
        loadingMutableStateFlow.value = true
        if (refresh || anime.value == null) {
            Log.d(TAG, "getAnime: from network")
            animeService.anime(
                id,
                onSuccess = this::onAnimeSuccess,
                onFailure = this::onAnimeFailure,
            )
            return
        }

        Log.d(TAG, "getAnime: from cache")
        loadingMutableStateFlow.value = false
    }

    private fun onAnimeSuccess(a: Anime) {
        anime.value = a
        a.userRate?.let {
            userRate.value = it
        }
        loadingMutableStateFlow.value = false
    }

    private fun onAnimeFailure(tr: Throwable) {}

    fun getRoles(id: Long, refresh: Boolean = false) {
        this.id = id
        loadingMutableStateFlow.value = true
        if (refresh || roles.value == null) {
            Log.d(TAG, "getRoles: from network")
            animeService.roles(
                id = id,
                onSuccess = this::onRolesSuccess,
//                TODO: handle error
            )
            return
        }

        Log.d(TAG, "getRoles: from cache")
        loadingMutableStateFlow.value = false
    }

    private fun onRolesSuccess(list: List<Role>) {
        roles.value = list
        loadingMutableStateFlow.value = false
    }

    fun setRate(id: Long?, body: PatchUserRate) {
        loadingMutableStateFlow.value = true
        if (id != null) {
            userRateService.updateUserRate(
                id = id,
                reqBody = body,
                onSuccess = this::onUserRateSuccess,
//                TODO: handle error
            )
        } else {
            createRate(
                PostUserRate.newInstance(
                    targetId = anime.value!!.id,
                    targetType= TargetType.ANIME,
                    userId = settings.userId,
                    chapters = body.userRate.chapters,
                    episodes = body.userRate.episodes,
                    rewatches = body.userRate.rewatches,
                    score = body.userRate.score,
                    status = body.userRate.status,
                    volumes = body.userRate.volumes,
                )
            )
        }
    }

    private fun createRate(body: PostUserRate) {
        loadingMutableStateFlow.value = true
        userRateService.createUserRate(
            reqBody = body,
            onSuccess = this::onUserRateSuccess,
//            TODO: handle error
        )
    }

    private fun onUserRateSuccess(rate: UserRate) {
        userRate.value = rate
        loadingMutableStateFlow.value = false
    }

    fun onRefresh() {
        getAnime(id, true)
        getRoles(id, true)
    }

    companion object {
        private const val TAG = "[AnimeViewModel]"
    }
}