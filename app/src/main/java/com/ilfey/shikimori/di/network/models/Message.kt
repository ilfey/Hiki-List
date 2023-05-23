package com.ilfey.shikimori.di.network.models

import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.TargetType
import java.util.*


data class Message(
    val id: Long,
    val kind: Kind,
    val read: Boolean,
    val body: String,
    val html_body: String,
    val created_at: Date,
    val linked_id: Long,
    val linked_type: String, // TODO: move to enum Topic/Contest
    val linked: Linked,
    val from: User,
    val to: User,
) {
    data class Linked(
        val id: Long,
        val topic_url: String,
        val thread_id: Long,
        val topic_id: Long,
        val type: TargetType,
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

    data class User(
        val id: Long,
        val nickname: String,
        val avatar: String,
        val image: Image,
        val last_online_at: Date,
        val url: String,
    ){
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
