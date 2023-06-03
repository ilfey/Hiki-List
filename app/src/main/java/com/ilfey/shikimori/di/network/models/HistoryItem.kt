package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.entities.HistoryItem as eHistoryItem
import com.ilfey.shikimori.di.network.enums.Kind
import java.text.SimpleDateFormat

data class HistoryItem(
    val id: Long,
    val createdAt: String,
    val description: String,
    val target: Target?,
) {
    data class Target(
        val id: Long,
        val titleEn: String,
        val titleRu: String,
        val image: String,
        val url: String,
        val kind: Kind?,
        val kindString: String?,
        val score: String?,
        val status: String,
        val episodes: String?,
    )

    companion object {
        fun parseFromEntity(ctx: Context, e: eHistoryItem): HistoryItem {
            val dateFormat = SimpleDateFormat(
                "dd MMMM yyyy",
                ctx.resources.configuration.locales.get(0),
            )

            return HistoryItem(
                id = e.id,
                createdAt = dateFormat.format(e.created_at),
                description = e.description,
                target = parseTarget(ctx, e.target),
            )
        }

        private fun parseTarget(
            ctx: Context,
            target: eHistoryItem.Target?
        ): Target? {
            if (target == null) {
                return null
            }

            return Target(
                id = target.id,
                titleEn = target.name,
                titleRu = target.russian.ifEmpty { ctx.getString(R.string.no_title) },
                image = makeUrl(target.image.original),
                url = makeUrl(target.url),
                kind = target.kind,
                kindString = ctx.parseKind(target.kind),
                score = ctx.parseScore(target.score),
                status = ctx.parseStatus(
                    target.status,
                    target.aired_on,
                    target.released_on
                ),
                episodes = ctx.parseEpisodes(
                    target.status,
                    target.episodes,
                    0,
                    target.episodes_aired
                ),
            )
        }
    }
}
