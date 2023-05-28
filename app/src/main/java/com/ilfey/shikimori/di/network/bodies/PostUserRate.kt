package com.ilfey.shikimori.di.network.bodies

import com.google.gson.annotations.SerializedName
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.TargetType

data class PostUserRate(
    @SerializedName("user_rate")
    val userRate: UserRate,
) {
    data class UserRate(
        val target_id: Long,
        val target_type: String,
        val user_id: Long,
        val chapters: Int? = null,
        val episodes: Int? = null,
        val rewatches: Int? = null,
        val score: Int? = null,
        val status: String? = null,
        val volumes: Int? = null,
    )

    companion object {
        fun newInstance(
            targetId: Long,
            targetType: TargetType,
            userId: Long,
            chapters: Int? = null,
            episodes: Int? = null,
            rewatches: Int? = null,
            score: Int? = null,
            status: ListType? = null,
            volumes: Int? = null,
        ) = PostUserRate(
            UserRate(
                target_id = targetId,
                target_type = targetType.value,
                user_id = userId,
                chapters = chapters,
                episodes = episodes,
                rewatches = rewatches,
                score = score,
                status = status?.value,
                volumes = volumes,
            )
        )

        fun newInstance(
            targetId: Long,
            targetType: TargetType,
            userId: Long,
            chapters: Int? = null,
            episodes: Int? = null,
            rewatches: Int? = null,
            score: Int? = null,
            status: String? = null,
            volumes: Int? = null,
        ) = PostUserRate(
            UserRate(
                target_id = targetId,
                target_type = targetType.value,
                user_id = userId,
                chapters = chapters,
                episodes = episodes,
                rewatches = rewatches,
                score = score,
                status = status,
                volumes = volumes,
            )
        )
    }
}
