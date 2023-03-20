package com.ilfey.shikimori.ui.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class AnimeViewModel(
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val anime = MutableLiveData<Anime>()
    val roles = MutableLiveData<List<Role>>()
    fun getAnime(id: Long) {
        repository.anime(id).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        anime.value = it.response.body()
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

    fun setStatus(id: Long, body: PatchUserRate.UserRate) {
        if (anime.value?.user_rate != null) {
            repository.update_user_rate(
                id,
                PatchUserRate(body),
            ).enqueue {
                when (it) {
                    is Result.Failure -> {

                    }
                    is Result.Success -> {}
                }
            }
        } else {
            repository.create_user_rate(
                id,
                PatchUserRate(body),
            ).enqueue {
                when (it) {
                    is Result.Failure -> {

                    }
                    is Result.Success -> {}
                }
            }
        }
    }
}