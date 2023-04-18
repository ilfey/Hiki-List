package com.ilfey.shikimori.di.network.models

import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind
import java.util.*


/**
 * https://shikimori.me/api/doc/1.0/animes/index
 **/
data class AnimeItem(
    val id: Long,
    val name: String,
    val russian: String,
    val image: Image,
    val url: String,
    val kind: Kind,
    val score: String,
    val status: AnimeStatus,
    val episodes: Int,
    val episodes_aired: Int,
    val aired_on: Date,
    val released_on: Date,
) {
    data class Image(
        val original: String,
        val preview: String,
        val x96: String,
        val x48: String,
    )
}

