package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.entities.AnimeRate as eAnimeRate

data class AnimeRate(
    val id: Long,
    val score: String?,
    val scoreInt: Int,
    val episodes: Int,
    val rewatchers: Int,
    val user: User,
    val anime: AnimeItem,
) {
    data class User(
        val id: Long,
        val username: String,
        val avatar: String,
    )

    companion object {
        fun parseFromEntity(ctx: Context, e: eAnimeRate): AnimeRate {
            return AnimeRate(
                id = e.id,
                score = ctx.parseScore(e.anime.score, e.score),
                scoreInt = e.score,
                episodes = e.episodes,
                rewatchers = e.rewatchers,
                user = parseUser(e.user),
                anime = AnimeItem.parseFromEntity(ctx, e.anime, e.episodes),
            )
        }

        private fun parseUser(e: eAnimeRate.User): User {
            return User(
                id = e.id,
                username = e.nickname,
                avatar = e.avatar,
            )
        }
    }
}