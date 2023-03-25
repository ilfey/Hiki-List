package com.ilfey.shikimori.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.models.Favourites
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class FavoritesViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ListViewModel() {

    val favorites = MutableLiveData<Favourites>()

    init {
        getFavorites()
    }

    override fun onRefresh() {
        getFavorites()
    }

    fun getFavorites() {
        repository.favorites(
            settings.userId
        ).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        favorites.value = it.response.body()
                    }
                }
                is Result.Failure -> {}
            }
        }
    }
}