package com.ilfey.shikimori.ui.favorites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.network.models.Favorites
import com.ilfey.shikimori.di.network.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class FavoritesViewModel(
    private val userService: UserService,
) : ListViewModel() {

    var lastUsername: String? = null
    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()
    
    val favorites = MutableLiveData<Favorites>()

    val animes = MutableLiveData<List<Favorites.Entry>?>()
    val mangas = MutableLiveData<List<Favorites.Entry>?>()
    val ranobe = MutableLiveData<List<Favorites.Entry>?>()
    val characters = MutableLiveData<List<Favorites.Entry>?>()
    val people = MutableLiveData<List<Favorites.Entry>?>()
    val mangakas = MutableLiveData<List<Favorites.Entry>?>()
    val seyu = MutableLiveData<List<Favorites.Entry>?>()
    val producers = MutableLiveData<List<Favorites.Entry>?>()

    override fun onRefresh() {
        if (lastUsername != null) {
            getFavorites(lastUsername!!, true)
        } else {
            Log.e(TAG, "onRefresh: lastUsername can not be null")
        }
    }

    fun getFavorites(id: String, refresh: Boolean = false) {
        loadingMutableStateFlow.value = true
        
        if (refresh || favorites.value == null) {
            Log.d(TAG, "getFavorites: from network")
            userService.favorites(
                user = id,
                onSuccess = this::onFavoritesUpdate,
                onFailure = this::onFavoritesFailure,
            )
            return
        }
        Log.d(TAG, "getFavorites: from cache")

        animes.value = favorites.value?.animes
        mangas.value = favorites.value?.mangas
        ranobe.value = favorites.value?.ranobe
        characters.value = favorites.value?.characters
        people.value = favorites.value?.people
        mangakas.value = favorites.value?.mangakas
        seyu.value = favorites.value?.seyu
        producers.value = favorites.value?.producers

        loadingMutableStateFlow.value = false
    }

    private fun onFavoritesUpdate(f: Favorites) {
        favorites.value = f
        animes.value = f.animes
        mangas.value = f.mangas
        ranobe.value = f.ranobe
        characters.value = f.characters
        people.value = f.people
        mangakas.value = f.mangakas
        seyu.value = f.seyu
        producers.value = f.producers

        loadingMutableStateFlow.value = false
    }

    private fun onFavoritesFailure(tr:Throwable) {}

    companion object {
        private const val TAG = "[FavoritesViewModel]"
    }
}