package com.ilfey.shikimori.di.network.bodies

import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.enums.TargetType

data class PostUserRate(
    val user_rate: UserRate,
) {
    data class UserRate(
        val target_id: Long,
        val target_type: TargetType,
        val user_id: Long,
        val chapters: Int? = null,
        val episodes: Int? = null,
        val rewatches: Int? = null,
        val score: Int? = null,
        val status: ListTypes? = null,
        val volumes: Int? = null,
    )
}
