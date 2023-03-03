package com.ilfey.shikimori.di.network.models

data class Achievements(
    val id: Long,
    val neko_id: Long,
    val level: Int,
    val progress: Int,
    val user_id: Long,
    val created_at: String,
    val updated_at: String,
)