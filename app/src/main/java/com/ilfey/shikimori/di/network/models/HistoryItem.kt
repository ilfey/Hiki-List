package com.ilfey.shikimori.di.network.models

import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind

data class HistoryItem(
    val id: Long,
    val created_at: String,
    val description: String,
    val target: Target?,
) {
    data class Target(
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
        /**
         * "yy-mm-dd"
         * */
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
