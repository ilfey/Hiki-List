package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.TargetType
import com.ilfey.shikimori.di.network.entities.UserRate as eUserRate
import java.util.Date

data class UserRate(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetType: TargetType?,
    val score: Int,
    val list: ListType,
    val rewatches: Int,
    val episodes: Int,
    val volumes: Int,
    val chapters: Int,
    val createdAt: Date,
    val updatedAt: Date,
) {
    companion object {
        fun parseFromEntity(ctx: Context, e: eUserRate): UserRate {
            return UserRate(
                id = e.id,
                userId = e.user_id,
                targetId = e.target_id,
                targetType = e.target_type,
                score = e.score,
                list = e.status,
                rewatches = e.rewatches,
                episodes = e.episodes,
                volumes = e.volumes,
                chapters = e.chapters,
                createdAt = e.created_at,
                updatedAt = e.updated_at,
            )
        }
    }
}
