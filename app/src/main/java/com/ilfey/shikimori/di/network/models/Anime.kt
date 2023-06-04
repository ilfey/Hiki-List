package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.R
import java.util.Date
import com.ilfey.shikimori.di.network.entities.Anime as eAnime

data class Anime(
    val id: Long,
    val titleEn: String,
    val titleRu: String,
    val image: String,
    val kind: String?,
    val score: String?,
    val status: String,
    val episodes: String?,
    val rating: String?,
    val english: List<String>?,
    val japanese: List<String>?,
    val synonyms: List<String>?,
    val license_name_ru: String?,
    val duration: Int,
    val description: String?,
//    val description_html: String?,
//    val description_source: String?,
    val franchise: String?,
    val favoured: Boolean,
    val threadId: Long,
    val topicId: Long,
//    val myanimelist_id: Long,
    val scoresStats: List<eAnime.RatesScoresStats>?,
    val statusesStats: List<eAnime.RatesStatusesStats>?,
    val updatedAt: Date,
    val nextEpsAt: Date?,
    val fansubbers: List<String>?,
    val fandubbers: List<String>?,
    val licensors: List<String>?,
    val genres: List<eAnime.Genre>?,
    val studios: List<eAnime.Studio>?,
    val videos: List<Video>?,
    val screenshots: List<String>?,
    val userRate: UserRate?,
) {

    data class Video(
        val url: String,
        val image: String,
        val name: String,
    )

    companion object {
        fun parseFromEntity(ctx: Context, e: eAnime): Anime {
            val userRate = if (e.user_rate != null) UserRate.parseFromEntity(ctx, e.user_rate) else null

            return Anime(
                id = e.id,
                titleEn = e.name,
                titleRu = e.russian.ifEmpty { ctx.getString(R.string.no_title) },
                image = makeUrl(e.image.original),
                kind = ctx.parseKind(e.kind),
                score = ctx.parseScore(e.score, e.user_rate?.score),
                status = ctx.parseStatus(e.status, e.aired_on, e.released_on),
                episodes = ctx.parseEpisodes(e.status, e.episodes, e.user_rate?.episodes ?: 0, e.episodes_aired),
                rating = ctx.parseRating(e.rating),
                english = parseAltTitles(e.english),
                japanese = parseAltTitles(e.japanese),
                synonyms = parseAltTitles(e.synonyms),

                license_name_ru = e.license_name_ru,
                duration = e.duration,
                description = e.description,
                franchise = e.franchise,
                favoured = e.favoured,
                threadId = e.thread_id,
                topicId = e.topic_id,
                scoresStats = e.rates_scores_stats,
                statusesStats = e.rates_statuses_stats,
                updatedAt = e.updated_at,
                nextEpsAt = e.next_episode_at,
                fansubbers = e.fansubbers.ifEmpty { null },
                fandubbers = e.fandubbers.ifEmpty { null },
                licensors = e.licensors.ifEmpty { null },
                genres = e.genres,
                studios = e.studios.ifEmpty { null },
                videos = parseVideos(ctx, e.videos),

                screenshots = if (e.screenshots.isEmpty()) null else e.screenshots.map { makeUrl(it.original) },
                userRate = userRate,
            )
        }

        private fun parseVideos(ctx: Context, l: List<eAnime.Video>): List<Video>? {
            l.ifEmpty { return null }

            return l.map {
                Video(
                    url = it.url,
                    image = it.image_url,
                    name = if (it.name.isNullOrEmpty()) ctx.getString(R.string.no_title) else it.name
                )
            }
        }

        private fun parseAltTitles(l: List<String?>): List<String>? {
            if (l.isEmpty()) {
                return null
            }

            return l.filterNotNull().filter { it.isNotEmpty() }
        }
    }
}