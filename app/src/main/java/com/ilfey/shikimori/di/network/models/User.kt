package com.ilfey.shikimori.di.network.models

data class User(
    val id: Long,
    val nickname: String,
    val avatar: String,
    val image: Image,
    val last_online: String,
    val url: String,
    val name: String,
    val sex: String,
    val website: String?,
    val full_years: Int,
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