package com.ilfey.shikimori.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.network.models.Friend
import com.ilfey.shikimori.di.network.models.User
import com.ilfey.shikimori.di.network.services.UserService

class ProfileViewModel(
    private val userService: UserService,
) : ViewModel() {

    private var lastUsername: String? = null

    private val mutableUser = MutableLiveData<User>()
    private val mutableFriends = MutableLiveData<List<Friend>>()

    val user: LiveData<User>
        get() = mutableUser

    val friends: LiveData<List<Friend>>
        get() = mutableFriends

    fun onRefresh() {
        if (lastUsername != null) {
            getUser(lastUsername!!, refresh = true)
        } else {
            Log.e(TAG, "onRefresh: lastUsername cannot be null")
        }
    }

    fun getUser(username: String, refresh: Boolean = false) {
        if (refresh || mutableUser.value == null || lastUsername != username) {
            lastUsername = username
            Log.d(TAG, "getUser: load user from network")
            userService.user(
                id = username,
                onSuccess = this::onUserSuccess,
                onFailure = this::onUserFailure,
            )
        }
        Log.d(TAG, "getUser: load user from cache")
    }

    fun getFriends(username: String, refresh: Boolean = false) {
        if (refresh || mutableFriends.value == null || lastUsername != username) {
            lastUsername = username
            Log.d(TAG, "getFriends: load friends from network")
            userService.friends(
                user = username,
                onSuccess = this::onFriendsSuccess,
                onFailure = this::onFriendsFailure,
            )
        }
        Log.d(TAG, "getFriends: load friends from cache")
    }

    private fun onUserSuccess(user: User) {
        mutableUser.value = user
    }

    private fun onFriendsSuccess(l: List<Friend>) {
        mutableFriends.value = l
    }

    private fun onUserFailure(tr: Throwable) {}
    private fun onFriendsFailure(tr: Throwable) {}

    companion object {
        private const val TAG = "[ProfileViewModel]"
    }
}