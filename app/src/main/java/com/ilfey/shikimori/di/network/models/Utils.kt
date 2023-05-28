package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.enums.AnimeStatus
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.Rating
import com.ilfey.shikimori.utils.gone
import java.text.SimpleDateFormat
import java.util.*

fun makeUrl(path: String) = BuildConfig.APP_URL + path
fun parseScore(s: String) = s.toFloat() / 2

fun Context.df() = SimpleDateFormat(
    "dd MMMM yyyy",
    resources.configuration.locales.get(0),
)

fun Context.parseStatus(
    status: AnimeStatus,
    aired_on: Date?,
    released_on: Date?
) =
    when (status) {
        AnimeStatus.ANONS -> {
            if (aired_on != null) {
                getString(R.string.anons_for, df().format(aired_on))
            } else {
                getString(R.string.anons)
            }
        }
        AnimeStatus.ONGOING -> {
            if (aired_on != null) {
                getString(R.string.ongoing_from, df().format(aired_on))
            } else {
                getString(R.string.ongoing)
            }
        }
        AnimeStatus.RELEASED -> {
            if (released_on != null) {
                getString(R.string.released_on, df().format(released_on))
            } else {
                getString(R.string.released)
            }
        }
    }

fun Context.parseEpisodes(
    status: AnimeStatus,
    animeEpisodes: Int,
    userEpisodes: Int = 0,
    episodesAired: Int
): String? {
    /* YandereDev reference */

    if (animeEpisodes != 0) {
        return if (userEpisodes != 0) {
            if (status == AnimeStatus.RELEASED) {
                getString(
                    R.string.episodes_with_count,
                    userEpisodes,
                    animeEpisodes,
                )
            } else {
                getString(
                    R.string.episodes_of_with_count,
                    userEpisodes,
                    episodesAired,
                    animeEpisodes,
                )
            }
        } else {
            if (status == AnimeStatus.RELEASED || status == AnimeStatus.ANONS) {
                getString(
                    R.string.episodes,
                    animeEpisodes,
                )
            } else {
                getString(
                    R.string.episodes_of,
                    episodesAired,
                    animeEpisodes
                )
            }
        }
    } else {
        if (status == AnimeStatus.ONGOING) {
            return if (userEpisodes != 0) {
                getString(
                    R.string.episodes_of_with_null,
                    userEpisodes,
                    episodesAired,
                )
            } else {
                getString(
                    R.string.episodes_of_null,
                    episodesAired,
                )
            }
        } else {
            return null
        }
    }
}

fun Context.parseRating(rating: Rating?) = when (rating) {
    Rating.G -> getString(R.string.rating_g)
    Rating.NONE -> null
    Rating.PG -> getString(R.string.rating_pg)
    Rating.PG_13 -> getString(R.string.rating_pg_13)
    Rating.R -> getString(R.string.rating_r)
    Rating.R_PLUS -> getString(R.string.rating_r_plus)
    Rating.RX -> getString(R.string.rating_rx)
    else -> null
}

fun Context.parseKind(kind: Kind) = when (kind) {
    Kind.TV -> getString(R.string.type_tv)
    Kind.MOVIE -> getString(R.string.type_movie)
    Kind.OVA -> getString(R.string.type_ova)
    Kind.ONA -> getString(R.string.type_ona)
    Kind.SPECIAL -> getString(R.string.type_special)
    Kind.MUSIC -> getString(R.string.type_music)
    Kind.TV_13 -> getString(R.string.type_tv)
    Kind.TV_24 -> getString(R.string.type_tv)
    Kind.TV_48 -> getString(R.string.type_tv)
    else -> null
}