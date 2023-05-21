package com.ilfey.shikimori.ui.main

import androidx.lifecycle.ViewModel
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue

class MainViewModel(
    private val settings: AppSettings,
    private val repository: ShikimoriRepository,
) : ViewModel() {
    fun getUser() {
        repository.whoami().enqueue {
            when (it) {
                is Success -> {
                    if (it.response.isSuccessful && it.response.body() != null) {
                        settings.userId = it.response.body()!!.id
                        settings.username = it.response.body()!!.nickname
                    }
                }
                is Failure -> {}
            }
        }
    }
}