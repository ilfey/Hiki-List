package com.ilfey.shikimori.ui.settings

import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel(
    private val userService: UserService,
) : ViewModel() {

    private val logoutMutableStateFlow = MutableStateFlow(false)

    val logoutSuccessFlow: Flow<Boolean>
        get() = logoutMutableStateFlow

    fun logout() {
        userService.logout(
            onSuccess = { logoutMutableStateFlow.value = true },
            onFailure = this::onUserLogoutError,
        )
    }

    private fun onUserLogoutError(tr: Throwable) {}

    companion object {
        private const val TAG = "[SettingsViewModel]"
    }
}