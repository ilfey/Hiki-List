package com.ilfey.shikimori.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.CurrentUser
import com.ilfey.shikimori.di.network.services.UserRateService
import com.ilfey.shikimori.di.network.services.UserService

class ProfileViewModel(
    private val settings: AppSettings,
    private val userRateService: UserRateService,
    private val userService: UserService,
) : ViewModel() {

    val user = MutableLiveData<CurrentUser>()
    val rates = MutableLiveData<List<UserRate>>()

    fun onRefresh() {
        getUser()
        getUserAnimeRates()
    }

    fun getUser() {
        userService.currentUser(
            onSuccess = this::onUserSuccess,
            onFailure = this::onUserFailure,
        )
    }

    private fun onUserSuccess(user: CurrentUser) {
        settings.userId = user.id
        this.user.value = user
    }

    private fun onUserFailure(tr: Throwable) {}

    fun getUserAnimeRates() {
        userRateService.userRates(
            userId = settings.userId,
            targetType = TargetType.ANIME,
            onSuccess = this::onUserAnimeRatesSuccess,
        )
    }

    private fun onUserAnimeRatesSuccess(list: List<UserRate>) {
        rates.value = list
    }
}