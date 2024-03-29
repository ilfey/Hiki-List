package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.Locale
import java.util.Date

data class CurrentUser(
    val id: Long,
    val nickname: String,
    val avatar: String,
    val image: Image,
    val last_online_at: Date,
    val url: String,
    val name: String?,
    val sex: String,
    val website: String,
    val birth_on: Date?,
    val full_years: Int?,
    val locale: Locale,
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