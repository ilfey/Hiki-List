package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.ListType
import java.util.Date

data class AnimeRate(
    val id: Long,
    val score: Int,
    val status: ListType,
    val text: String?,
    val episodes: Int,
    val chapters: Int?,
    val volumes: Int?,
    val text_html: String,
    val rewatchers: Int,
    val created_at: String,
    val updated_at: String,
    val user: User,
    val anime: AnimeItem,
    val manga: Any?,
) {
    data class User(
        val id: Long,
        val nickname: String,
        val avatar: String,
        val image: Image,
        val last_online_at: Date,
        val url: String,
    ) {
        data class Image(
            val x160: String,
            val x148: String,
            val x80: String,
            val x64: String,
            val x48: String,
            val x32: String,
            val x16: String,
        )
    }
}