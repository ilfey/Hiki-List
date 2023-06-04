package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.entities.Friend as eFriend

data class Friend(
    val id: Long,
    val username: String,
    val avatar: String,
    val image: String,
    val lastOnline: String,
    val url: String,
) {
    companion object {
        fun parseFromEntity(ctx: Context, e: eFriend): Friend {
            return Friend(
                id = e.id,
                username = e.nickname,
                avatar = e.avatar,
                image = e.image.x160,
                lastOnline = ctx.df().format(e.last_online_at),
                url = e.url,
            )
        }
    }
}
