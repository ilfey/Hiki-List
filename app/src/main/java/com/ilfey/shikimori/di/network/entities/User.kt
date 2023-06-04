package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.Rate
import com.ilfey.shikimori.di.network.enums.TargetType
import java.util.*

data class User(
    val id: Long,
    val nickname: String,
    val avatar: String,
    val image: Image,
    val last_online_at: Date,
    val url: String,
    val name: String?,
    val sex: String?,
    val full_years: Int?,
    val last_online: String,
    val website: String,
//    val location: Any?,
    val banned: Boolean,
    val about: String,
    val about_html: String,
    val common_info: List<String>,
    val show_comments: Boolean,
    val in_friends: Boolean?,
    val is_ignored: Boolean,
    val stats: Stats,
    val style_id: Long,
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

    data class Stats(
        val statuses: Statuses,
        val full_statuses: Statuses,
        val scores: Scores,
        val types: Types,
        val ratings: Ratings,
//        val has_anime?: Boolean,
//        val has_manga?: Boolean,
//        val genres: List<*>,
//        val studios: List<*>,
//        val publishers: List<*>,
//        val activity: List<Activity>,
    ) {
        data class Statuses(
            val anime: List<Status>,
            val manga: List<Status>,
        ) {
            data class Status(
                val id: Long,
                val grouped_id: String,
                val name: ListType,
                val size: Int,
                val type: TargetType, // Anime, Manga
            )
        }

        data class Scores(
            val anime: List<Score>,
            val manga: List<Score>,
        ) {
            data class Score(
                val name: Rate,
                val value: Int,
            )
        }

        data class Types(
            val anime: List<Type>,
            val manga: List<Type>,
        ) {
            data class Type(
                val name: String,
                val value: Int,
            )
        }

        data class Ratings(
            val anime: List<Rating>,
        ){
            data class Rating(
                val name: String,
                val value: Int,
            )
        }
    }
}