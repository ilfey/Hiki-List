package com.ilfey.shikimori.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.models.User
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result

class HomeViewModel(
    private val storage: Storage,
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val user = MutableLiveData<User>()
    val user_rates = MutableLiveData<List<UserRate>>()

    init {
        onRefresh()
    }

    fun onRefresh() {
        getUser {
            getUserRates(it.id)
        }
    }

    fun getUser(callback: ((User) -> Unit)? = null) {
        repository.whoami().enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        storage.user_id = it.response.body()!!.id
                        user.value = it.response.body()
                        callback?.invoke(it.response.body()!!)
                    }
                }
                is Result.Failure -> {}
            }
        }
    }

    fun getUserRates(user_id: Long) {
        repository.user_rates(
            user_id = user_id
        ).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        user_rates.value = it.response.body()
                    }
                }
                is Result.Failure -> {}
            }
        }
    }
}