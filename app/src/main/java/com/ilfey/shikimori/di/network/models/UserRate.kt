package com.ilfey.shikimori.di.network.models

import android.os.Parcelable
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.TargetType
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRate(
    val id: Long?,
    val user_id: Long?,
    val target_id: Long?,
    val target_type: TargetType?,
    val score: Int,
    val status: ListType,
    val rewatches: Int,
    val episodes: Int,
    val volumes: Int,
    val chapters: Int,
    val text: String?,
    val text_html: String?,
    val created_at: String,
    val updated_at: String,
) : Parcelable

fun List<UserRate>.filterByStatus(status: ListType) = this.filter { it.status == status }
fun List<UserRate>.filterByTargetType(target_type: TargetType) =
    this.filter { it.target_type == target_type }