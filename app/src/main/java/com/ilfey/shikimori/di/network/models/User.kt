package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.Rate
import com.ilfey.shikimori.di.network.entities.User as eUser

data class User(
    val id: Long,
    val username: String,
    val avatar: String,
    val age: String?,
    val lastOnline: String,
    val website: String?,
    val in_friends: Boolean,
    val is_ignored: Boolean,
    val stats: Stats,
) {
    data class Stats(
        val statuses: Statuses,
        val scores: Scores,
        val types: Types,
        val ratings: Ratings,
    ) {
        data class Statuses(
            val anime: List<Status>,
            val manga: List<Status>,
        ) {
            data class Status(
                val list: ListType,
                val size: Int,
            )
        }

        data class Scores(
            val anime: List<Score>,
            val manga: List<Score>,
        ) {
            data class Score(
                val rate: Rate,
                val count: Int,
            )
        }

        data class Types(
            val anime: List<Type>,
            val manga: List<Type>,
        ) {
            data class Type(
                val type: String,
                val count: Int,
            )
        }

        data class Ratings(
            val anime: List<Rating>,
        ) {
            data class Rating(
                val rating: String,
                val count: Int,
            )
        }
    }

    companion object {
        fun parseFromEntity(ctx: Context, e: eUser): User {
            return User(
                id = e.id,
                username = e.nickname,
                avatar = e.image.x160,
                age = ctx.parseAge(e.full_years),
                lastOnline = e.last_online,
                website = if (e.website != "") "https://" + e.website else null,
                in_friends = e.in_friends ?: false,
                is_ignored = e.is_ignored,
                stats = parseStats(ctx, e.stats)
            )
        }

        private fun parseStats(ctx: Context, s: eUser.Stats): Stats {
            return Stats(
                statuses = Stats.Statuses(
                    anime = s.full_statuses.anime.map {
                        Stats.Statuses.Status(
                            list = it.name,
                            size = it.size,
                        )
                    },
                    manga = s.full_statuses.manga.map {
                        Stats.Statuses.Status(
                            list = it.name,
                            size = it.size,
                        )
                    },
                ),
                scores = Stats.Scores(
                    anime = s.scores.anime.map {
                        Stats.Scores.Score(
                            rate = it.name,
                            count = it.value,
                        )
                    },
                    manga = s.scores.manga.map {
                        Stats.Scores.Score(
                            rate = it.name,
                            count = it.value,
                        )
                    }
                ),
                types = Stats.Types(
                    anime = s.types.anime.map {
                        Stats.Types.Type(
                            type = it.name,
                            count = it.value,
                        )
                    },
                    manga = s.types.manga.map {
                        Stats.Types.Type(
                            type = it.name,
                            count = it.value,
                        )
                    },
                ),
                ratings = Stats.Ratings(
                    anime = s.ratings.anime.map {
                        Stats.Ratings.Rating(
                            rating = it.name,
                            count = it.value,
                        )
                    },
                ),
            )
        }
    }
}
