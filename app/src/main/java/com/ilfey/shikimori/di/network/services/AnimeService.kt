package com.ilfey.shikimori.di.network.services

import android.content.Context
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.apis.AnimeApi
import com.ilfey.shikimori.di.network.enums.*
import com.ilfey.shikimori.di.network.models.Anime
import com.ilfey.shikimori.di.network.models.AnimeItem
import com.ilfey.shikimori.di.network.models.Role
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*

class AnimeService(
    private val animeApi: AnimeApi,
    private val settings: AppSettings,
    private val context: Context,
) {

    fun animes(
        page: Int = 1,
        count: Int = 50,
        list: ListType? = null,
        search: String? = null,
        onSuccess: (List<AnimeItem>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        animeApi.animes(
            page = page,
            limit = count,
            censored = !settings.isNsfwEnable,
            list = list?.value,
            search = search,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item ->
                            AnimeItem.parseFromEntity(context, item)
                        })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun anime(
        id: Long,
        onSuccess: (Anime) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        animeApi.anime(
            id = id,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(Anime.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun roles(
        id: Long,
        onSuccess: (List<Role>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        animeApi.animeRoles(
            id = id,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item -> Role.parseFromEntity(context, item) })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }
}