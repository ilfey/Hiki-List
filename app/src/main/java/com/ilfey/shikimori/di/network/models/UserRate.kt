package com.ilfey.shikimori.di.network.models

import com.ilfey.shikimori.di.network.enums.ListTypes
import com.ilfey.shikimori.di.network.enums.TargetType

data class UserRate(
    val id: Long,
    val user_id: Long,
    val target_id: Long,
    val target_type: TargetType,
    val score: Int,
    val status: ListTypes,
    val rewatches: Int,
    val episodes: Int,
    val volumes: Int,
    val chapters: Int,
    val text: String?,
    val text_html: String?,
    val created_at: String,
    val updated_at: String,
)

fun List<UserRate>.filterByStatus(status: ListTypes) = this.filter { it.status == status }
fun List<UserRate>.filterByTargetType(target_type: TargetType) =
    this.filter { it.target_type == target_type }