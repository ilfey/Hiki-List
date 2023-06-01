package com.ilfey.shikimori.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.enums.Locale
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
        getUser(refresh = true)
        getUserAnimeRates(refresh = true)
    }

    fun getUser(refresh: Boolean = false) {
        if (refresh || user.value == null) {
            Log.d(TAG, "getUser: load current user from network")
            userService.currentUser(
                onSuccess = this::onUserSuccess,
                onFailure = this::onUserFailure,
            )
        }
        Log.d(TAG, "getUser: load user from cache")
    }

    private fun onUserSuccess(user: CurrentUser) {
        settings.userId = user.id
        settings.username = user.username
        settings.isEnLocale = user.locale == Locale.EN
        this.user.value = user
    }

    private fun onUserFailure(tr: Throwable) {}

    fun getUserAnimeRates(refresh: Boolean = false) {
        if (refresh || rates.value == null) {
            Log.d(TAG, "getUserAnimeRates: load rates from network")
            userRateService.userRates(
                userId = settings.userId,
                targetType = TargetType.ANIME,
                onSuccess = this::onUserAnimeRatesSuccess,
            )
        }

        Log.d(TAG, "getUserAnimeRates: load rates from cache")
    }

    private fun onUserAnimeRatesSuccess(list: List<UserRate>) {
        rates.value = list
    }

    companion object {
        private const val TAG = "[ProfileViewModel]"
    }
}