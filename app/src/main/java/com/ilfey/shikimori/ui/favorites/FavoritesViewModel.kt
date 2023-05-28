package com.ilfey.shikimori.ui.favorites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.network.apis.UserApi
import com.ilfey.shikimori.di.network.entities.Favorites
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class FavoritesViewModel(
    private val userApi: UserApi,
) : ListViewModel() {

    private var lastUsername: String? = null
    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()
    
    val favorites = MutableLiveData<Favorites>()

    val animes = MutableLiveData<List<Favorites.Entry>>()
    val mangas = MutableLiveData<List<Favorites.Entry>>()
    val ranobe = MutableLiveData<List<Favorites.Entry>>()
    val characters = MutableLiveData<List<Favorites.Entry>>()
    val people = MutableLiveData<List<Favorites.Entry>>()
    val mangakas = MutableLiveData<List<Favorites.Entry>>()
    val seyu = MutableLiveData<List<Favorites.Entry>>()
    val producers = MutableLiveData<List<Favorites.Entry>>()

    override fun onRefresh() {
        lastUsername?.let { getFavorites(it, true) }.also {
            Log.e(TAG, "onRefresh: lastUsername can not be null")
        }
    }

    fun getFavorites(id: String, refresh: Boolean = false) {
        loadingMutableStateFlow.value = true
        
        if (refresh || favorites.value == null) {
            Log.d(TAG, "getFavorites: from network")
            userApi.favorites(
                id = id,
            ).enqueue {
                when (it) {
                    is Result.Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            val body = it.response.body()!!
                            favorites.value = body
                            animes.value = body.animes
                            mangas.value = body.mangas
                            ranobe.value = body.ranobe
                            characters.value = body.characters
                            people.value = body.people
                            mangakas.value = body.mangakas
                            seyu.value = body.seyu
                            producers.value = body.producers
                        }
                    }
                    is Result.Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }
        Log.d(TAG, "getFavorites: from cache")

        animes.value = favorites.value!!.animes
        mangas.value = favorites.value!!.mangas
        ranobe.value = favorites.value!!.ranobe
        characters.value = favorites.value!!.characters
        people.value = favorites.value!!.people
        mangakas.value = favorites.value!!.mangakas
        seyu.value = favorites.value!!.seyu
        producers.value = favorites.value!!.producers

        loadingMutableStateFlow.value = false
    }
    
    companion object {
        private const val TAG = "[FavoritesViewModel]"
    }
}