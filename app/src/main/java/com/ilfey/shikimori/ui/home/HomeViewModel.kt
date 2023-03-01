package com.ilfey.shikimori.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.models.User
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result

class HomeViewModel(
    private val storage: Storage,
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val user = MutableLiveData<User>()

    init {
        getUser()
    }

    fun getUser() {
        repository.whoami().enqueue {
            when (it) {
                is Result.Success -> {
                    val res = it.response.body()
                    if (it.response.isSuccessful && res != null) {
                        storage.user = res
                        user.value = res!!
                    }
                }
                is Result.Failure -> {}
            }
        }
    }
}