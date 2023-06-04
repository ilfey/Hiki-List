package com.ilfey.shikimori.di.network.services

import android.content.Context
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.apis.UserApi
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.*

class UserService(
    private val userApi: UserApi,
    private val settings: AppSettings,
    private val context: Context,
) {
    fun user(
        id: String,
        onSuccess: (User) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.user(
            id = id,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(
                            User.parseFromEntity(context, body)
                        )
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun currentUser(
        onSuccess: (CurrentUser) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.whoami().enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(
                            CurrentUser.parseFromEntity(context, body)
                        )
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun logout(
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.signOut().enqueue {
            when (it) {
                is Success -> {
                    if (it.response.isSuccessful) {
                        onSuccess()
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun friends(
        user: String,
        onSuccess: (List<Friend>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.friends(
            id = user,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item ->
                            Friend.parseFromEntity(context, item)
                        })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun favorites(
        user: String,
        onSuccess: (Favorites) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.favorites(
            id = user,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(Favorites.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun favorites(
        userId: Long,
        onSuccess: (Favorites) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.favorites(
            id = userId,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(Favorites.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun history(
        user: String,
        page: Int = 1,
        count: Int = 100,
        targetId: Long? = null,
        targetType: TargetType? = null,
        onSuccess: (List<HistoryItem>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.history(
            id = user,
            page = page,
            limit = count,
            target_id = targetId,
            target_type = targetType?.value,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item ->
                            HistoryItem.parseFromEntity(context, item)
                        })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun animeRates(
        user: String,
        page: Int = 1,
        count: Int = 5000,
        status: ListType,
        onSuccess: (List<AnimeRate>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.animeRates(
            id = user,
            page = page,
            limit = count,
            status = status.value,
            censored = !settings.isNsfwEnable,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item ->
                            AnimeRate.parseFromEntity(context, item)
                        })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun animeRates(
        userId: Long,
        page: Int = 1,
        count: Int = 5000,
        status: ListType,
        onSuccess: (List<AnimeRate>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userApi.animeRates(
            id = userId,
            page = page,
            limit = count,
            status = status.value,
            censored = !settings.isNsfwEnable,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item ->
                            AnimeRate.parseFromEntity(context, item)
                        })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }
}