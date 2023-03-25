package com.ilfey.shikimori.ui.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.bodies.PostUserRate
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class AnimeViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val anime = MutableLiveData<Anime>()
    val userRate = MutableLiveData<UserRate>()
    val roles = MutableLiveData<List<Role>>()
    fun getAnime(id: Long) {
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
        }
    }

    fun getRoles(id: Long) {
        repository.animeRoles(id).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        roles.value = it.response.body()!!
                    }
                }
                is Result.Failure -> {}
            }
        }
    }

    fun setRate(id: Long?, body: PatchUserRate.UserRate) {
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
}