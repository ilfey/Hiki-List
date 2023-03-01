package com.ilfey.shikimori.di.network.models

data class Achievements(
    val id: Int,
    val neko_id: String,
    val level: Int,
    val progress: Int,
    val user_id: Int,
    val created_at: String,
    val updated_at: String,
)