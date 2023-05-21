package com.ilfey.shikimori.ui.anime

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.bodies.PostUserRate
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimeViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
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
            repository.anime(id).enqueue {
                when (it) {
                    is Result.Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            anime.value = it.response.body()
                            if (it.response.body()?.user_rate != null) {
                                userRate.value = it.response.body()!!.user_rate!!
                            }
                        }
                    }
                    is Result.Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }

        Log.d(TAG, "getAnime: from cache")
        loadingMutableStateFlow.value = false
    }

    fun getRoles(id: Long, refresh: Boolean = false) {
        this.id = id
        loadingMutableStateFlow.value = true
        if (refresh || roles.value == null) {
            Log.d(TAG, "getRoles: from network")
            repository.animeRoles(id).enqueue {
                when (it) {
                    is Result.Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            roles.value = it.response.body()!!
                        }
                    }
                    is Result.Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }

        Log.d(TAG, "getRoles: from cache")
        loadingMutableStateFlow.value = false
    }

    fun setRate(id: Long?, body: PatchUserRate.UserRate) {
        loadingMutableStateFlow.value = true
        if (id != null) {
            repository.update_user_rate(
                id,
                PatchUserRate(body),
            ).enqueue {
                when (it) {
                    is Result.Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            userRate.value = it.response.body()
                        }
                    }
                    is Result.Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
        } else {
            createRate(
                PostUserRate.UserRate(
                    anime.value!!.id,
                    TargetType.ANIME,
                    settings.userId,
                    body.chapters,
                    body.episodes,
                    body.rewatches,
                    body.score,
                    body.status,
                    body.volumes,
                )
            )
        }
    }

    private fun createRate(body: PostUserRate.UserRate) {
        repository.create_user_rate(
            PostUserRate(body),
        ).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        userRate.value = it.response.body()
                    }
                }
                is Result.Failure -> {}
            }
        }
    }

    fun onRefresh() {
        getAnime(id, true)
        getRoles(id, true)
    }

    companion object {
        private const val TAG = "[AnimeViewModel]"
    }
}