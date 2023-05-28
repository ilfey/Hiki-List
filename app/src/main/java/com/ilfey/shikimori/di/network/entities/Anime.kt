package com.ilfey.shikimori.di.network.entities

import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.enums.Rating
import java.util.Date

data class Anime(
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
    val rating: Rating?,
    val english: List<String?>,
    val japanese: List<String?>,
    val synonyms: List<String?>,
    val license_name_ru: String?,
    val duration: Int,
    val description: String?,
//    val description_html: String?, // TODO: Check
//    val description_source: String?, // TODO: Check
    val franchise: String?,
    val favoured: Boolean,
    val anons: Boolean,
    val ongoing: Boolean,
    val thread_id: Long,
    val topic_id: Long,
//    val myanimelist_id: Long,
    val rates_scores_stats: List<RatesScoresStats>,
    val rates_statuses_stats: List<RatesStatusesStats>,
    val updated_at: Date,
    val next_episode_at: Date?,
    val fansubbers: List<String>,
    val fandubbers: List<String>,
    val licensors: List<String>,
    val genres: List<Genre>,
    val studios: List<Studio>,
    val videos: List<Video>,
    val screenshots: List<Screenshot>,
    val user_rate: UserRate?,
) {
    data class Image(
        val original: String,
        val preview: String,
        val x96: String,
        val x48: String,
    )

    data class RatesScoresStats(
        val name: Int,
        val value: Int,
    )

    data class RatesStatusesStats(
        val name: ListType,
        val value: Int,
    )

    data class Genre(
        val id: Long,
        val name: String,
        val russian: String,
        val kind: String, // Anime
    )

    data class Studio(
        val id: Long,
        val name: String,
        val filtered_name: String,
        val real: Boolean,
        val image: String,
    )

    data class Video(
        val id: Long,
        val url: String,
        val image_url: String,
        val player_url: String,
        val name: String,
        val kind: String, // Op
        val hosting: String,
    )

    data class Screenshot(
        val original: String,
        val preview: String,
    )
}