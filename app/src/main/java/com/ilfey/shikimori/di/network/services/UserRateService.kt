package com.ilfey.shikimori.di.network.services

import android.content.Context
import com.ilfey.shikimori.di.AppSettings
import com.ilfey.shikimori.di.network.apis.UserRateApi
import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.bodies.PostUserRate
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.models.UserRate
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.enqueue
import com.ilfey.shikimori.utils.RetrofitEnqueue.Companion.Result.*

class UserRateService(
    private val userRateApi: UserRateApi,
    private val settings: AppSettings,
    private val context: Context,
) {
    fun userRate(
        id: Long,
        onSuccess: (UserRate) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userRateApi.userRate(
            id = id,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(UserRate.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun userRates(
        userId: Long,
        page: Int = 1,
        count: Int = 1000,
        targetId: Long? = null,
        targetType: TargetType? = null,
        status: String? = null,
        onSuccess: (List<UserRate>) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userRateApi.userRates(
            user_id = userId,
            target_id = targetId,
            target_type = targetType,
            status = status,
            page = page,
            limit = count,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(body.map { item -> UserRate.parseFromEntity(context, item) })
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun createUserRate(
        targetId: Long,
        targetType: TargetType,
        chapters: Int? = null,
        episodes: Int? = null,
        rewatches: Int? = null,
        score: Int? = null,
        status: ListType? = null,
        volumes: Int? = null,
        onSuccess: (UserRate) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        val reqBody = PostUserRate.newInstance(
            targetId = targetId,
            targetType = targetType,
            userId = settings.userId,
            chapters = chapters,
            episodes = episodes,
            rewatches = rewatches,
            score = score,
            status = status,
            volumes = volumes,
        )

        userRateApi.createUserRate(
            body = reqBody,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(UserRate.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun createUserRate(
        reqBody: PostUserRate,
        onSuccess: (UserRate) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userRateApi.createUserRate(
            body = reqBody,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(UserRate.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun updateUserRate(
        id: Long,
        chapters: Int? = null,
        episodes: Int? = null,
        rewatches: Int? = null,
        score: Int? = null,
        status: ListType? = null,
        volumes: Int? = null,
        onSuccess: (UserRate) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        val reqBody = PatchUserRate.newInstance(
            chapters = chapters,
            episodes = episodes,
            rewatches = rewatches,
            score = score,
            status = status,
            volumes = volumes,
        )

        userRateApi.updateUserRate(
            id = id,
            body = reqBody,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(UserRate.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }

    fun updateUserRate(
        id: Long,
        reqBody: PatchUserRate,
        onSuccess: (UserRate) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        userRateApi.updateUserRate(
            id = id,
            body = reqBody,
        ).enqueue {
            when (it) {
                is Success -> {
                    val body = it.response.body()
                    if (it.response.isSuccessful && body != null) {
                        onSuccess(UserRate.parseFromEntity(context, body))
                    }
                }
                is Failure -> onFailure(it.error)
            }
        }
    }
}