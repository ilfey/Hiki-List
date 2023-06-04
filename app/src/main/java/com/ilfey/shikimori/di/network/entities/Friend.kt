package com.ilfey.shikimori.di.network.entities

import java.util.*

data class Friend(
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
