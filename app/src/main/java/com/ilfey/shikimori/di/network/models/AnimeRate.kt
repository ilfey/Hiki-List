package com.ilfey.shikimori.di.network.models

import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.ListTypes

data class AnimeRate(
    val id: Long,
    val score: Int,
    val status: ListTypes,
    val text: String?,
    val episodes: Int,
    val chapters: Int?, // TODO: Check
    val volumes: Int?, // TODO: Check
    val text_html: String,
    val rewatchers: Int,
    val created_at: String,
    val updated_at: String,
    val user: User,
    val anime: Anime,
    val manga: Any?,
) {
    data class User(
        val id: Long,
        val nickname: String,
        val avatar: String,
        val image: Image,
        val last_online_at: String,
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

    data class Anime(
        val id: Long,
        val name: String,
        val russian: String,
        val image: Image,
        val url: String,
        val kind: Kind,
        val score: String,
        val episodes: Int,
        val episodes_aired: Int,
        val aired_on: String?,
        val released_on: String?,
    ) {
        data class Image(
            val original: String,
            val preview: String,
            val x96: String,
            val x48: String,
        )
    }
}

fun List<AnimeRate>.filterByAnimeListStatus(list: ListTypes) = filter { it.status == list }