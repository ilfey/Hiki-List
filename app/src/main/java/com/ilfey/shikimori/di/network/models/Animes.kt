package com.ilfey.shikimori.di.network.models


/**
 * https://shikimori.one/api/doc/1.0/animes/index
 **/
data class Animes(
    val id: Long,
    val name: String,
    val russian: String,
    val image: Image,
    val url: String,
    val kind: String,
    val score: String,
    val status: String,
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

