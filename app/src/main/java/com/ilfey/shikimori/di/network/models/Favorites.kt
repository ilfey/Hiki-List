package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.entities.Favorites as eFavorites

data class Favorites(
    val animes: List<Entry>?,
    val mangas: List<Entry>?,
    val ranobe: List<Entry>?,
    val characters: List<Entry>?,
    val people: List<Entry>?,
    val mangakas: List<Entry>?,
    val seyu: List<Entry>?,
    val producers: List<Entry>?,
) {
    data class Entry(
        val id: Long,
        val titleEn: String,
        val titleRu: String,
        val image: String,
    )
    companion object {
        fun parseFromEntity(ctx: Context, e: eFavorites): Favorites {
            return Favorites(
                animes = if (e.animes.isNotEmpty()) e.animes.map { parseEntry(it) } else null,
                mangas = if (e.mangas.isNotEmpty()) e.mangas.map { parseEntry(it) } else null,
                ranobe = if (e.ranobe.isNotEmpty()) e.ranobe.map { parseEntry(it) } else null,
                characters = if (e.characters.isNotEmpty()) e.characters.map { parseEntry(it) } else null,
                people = if (e.people.isNotEmpty()) e.people.map { parseEntry(it) } else null,
                mangakas = if (e.mangakas.isNotEmpty()) e.mangakas.map { parseEntry(it) } else null,
                seyu = if (e.seyu.isNotEmpty()) e.seyu.map { parseEntry(it) } else null,
                producers = if (e.producers.isNotEmpty()) e.producers.map { parseEntry(it) } else null,
            )
        }

        private fun parseEntry(entry: eFavorites.Entry): Entry {
            return Entry(
                id= entry.id,
                titleEn= entry.name,
                titleRu= entry.russian,
                image = makeUrl(entry.image.replace("x64", "original")),
            )
        }
    }
}

