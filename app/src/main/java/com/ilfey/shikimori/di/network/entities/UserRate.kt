package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.TargetType
import java.util.*

data class UserRate(
    val id: Long,
    val user_id: Long,
    val target_id: Long,
    val target_type: TargetType,
    val score: Int,
    val status: ListType,
    val rewatches: Int,
    val episodes: Int,
    val volumes: Int,
    val chapters: Int,
    val text: String?,
    val text_html: String?,
    val created_at: Date,
    val updated_at: Date,
)