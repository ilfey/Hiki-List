package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind
import java.util.Date

data class HistoryItem(
    val id: Long,
    val created_at: Date,
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
        val aired_on: Date?,
        val released_on: Date?,
    ) {
        data class Image(
            val original: String,
            val preview: String,
            val x96: String,
            val x48: String,
        )
    }
}
