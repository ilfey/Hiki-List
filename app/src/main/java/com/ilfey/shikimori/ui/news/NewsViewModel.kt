package com.ilfey.shikimori.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ilfey.shikimori.base.ListViewModel
import com.ilfey.shikimori.di.network.ShikimoriRepository
import com.ilfey.shikimori.di.network.enums.MessageType
import com.ilfey.shikimori.di.network.models.Message
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsViewModel(
    private val repository: ShikimoriRepository
) : ListViewModel() {
    private var lastUsername: String? = null

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    val news = MutableLiveData<List<Message>>()

    fun getNews(id: String, refresh: Boolean = false) {
        loadingMutableStateFlow.value = true
        if (refresh || news.value == null) {
            Log.d(TAG, "getNews: from network")
            repository.messages(
                id = id,
                type = MessageType.NEWS,
            ).enqueue {
                when (it) {
                    is Success -> {
                        if (it.response.isSuccessful && it.response.body() != null) {
                            news.value = it.response.body()
                        }
                    }
                    is Failure -> {}
                }
                loadingMutableStateFlow.value = false
            }
            return
        }
        Log.d(TAG, "getNews: from cache")
        loadingMutableStateFlow.value = false
    }

    override fun onRefresh() {
        lastUsername?.let {
            getNews(it, true)
        }.also {
            Log.d(TAG, "onRefresh: lastUsername cannot be null")
        }
    }

    companion object {
        private const val TAG = "[NewsViewModel]"
    }
}