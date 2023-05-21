package com.ilfey.shikimori.di.network.models

data class Favorites(
    val animes: List<Entry>,
    val mangas: List<Entry>,
    val ranobe: List<Entry>,
    val characters: List<Entry>,
    val people: List<Entry>,
    val mangakas: List<Entry>,
    val seyu: List<Entry>,
    val producers: List<Entry>,
) {
    data class Entry(
        val id: Long,
        val name: String,
        val russian: String,
        val image: String,
        /**
         * Always is null
         * */
        val url: String?,
    )
}
