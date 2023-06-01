package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.entities.AnimeItem as eAnimeItem

data class AnimeItem(
    val id: Long,
    val titleEn: String,
    val titleRu: String,
    val image: String,
    val url: String,
    val kind: Kind?,
    val score: Float,
    val status: String,
    val episodes: String?,
    val userEpisodes: Int,
    val episodesAired: Int,
) {
    companion object {
        fun parseFromEntity(ctx: Context, e: eAnimeItem, userEpisodes: Int = 0): AnimeItem {
            return AnimeItem(
                id = e.id,
                titleEn = e.name,
                titleRu = e.russian.ifEmpty { ctx.getString(R.string.no_title) },
                image = makeUrl(e.image.original),
                url = makeUrl(e.url),
                kind = e.kind,
                score = parseScore(e.score),
                status = ctx.parseStatus(e.status, e.aired_on, e.released_on),
                episodes = ctx.parseEpisodes(
                    e.status,
                    e.episodes,
                    userEpisodes,
                    e.episodes_aired
                ),
                userEpisodes = userEpisodes,
                episodesAired = e.episodes_aired,
            )
        }
    }
}