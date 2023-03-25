package com.ilfey.shikimori.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.models.User
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result

class ProfileViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ViewModel() {

    val user = MutableLiveData<User>()
    val rates = MutableLiveData<List<UserRate>>()

    fun onRefresh() {
        getUser {
            getRates(it.id)
        }
    }

    fun getUser(callback: ((User) -> Unit)? = null) {
        repository.whoami().enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        settings.userId = it.response.body()!!.id
                        user.value = it.response.body()
                        callback?.invoke(it.response.body()!!)
                    }
                }
                is Result.Failure -> {}
            }
        }
    }

    fun getRates(user_id: Long) {
        repository.user_rates(
            user_id = user_id
        ).enqueue {
            when (it) {
                is Result.Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        rates.value = it.response.body()
                    }
                }
                is Result.Failure -> {}
            }
        }
    }
}