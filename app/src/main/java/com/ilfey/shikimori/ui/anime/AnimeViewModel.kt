package com.ilfey.shikimori.ui.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class AnimeViewModel(
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val anime = MutableLiveData<Anime>()

    fun getAnime(id: Long) {
        repository.anime(
            id = id,
        ).enqueue {
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
}