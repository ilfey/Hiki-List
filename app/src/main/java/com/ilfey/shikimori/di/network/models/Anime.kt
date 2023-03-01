package com.ilfey.shikimori.di.network.models

data class Anime(
    val id: Long,
    val name: String,
    val russian: String,
    val image: Image,
    val url: String,
    val kind: String,
    val score: String,
    val status: String,
    val episodes: Int,
    val episodes_aired: Int,
    val aired_on: String?,
    val released_on: String?,
    val rating: String?,
    val english: List<String>, // TODO: Check
    val japanese: List<String>, // TODO: Check
    val synonyms: List<*>, // TODO: Check
    val license_name_ru: String?, // TODO: Check
    val duration: Int, // TODO: Check
    val description: String?, // TODO: Check
//    val description_html: String?, // TODO: Check
//    val description_source: String?, // TODO: Check
    val franchise: String?, // TODO: Check
    val favoured: Boolean,
    val anons: Boolean,
    val thread_id: Long,
    val topic_id: Long,
    val myanimelist_id: Long,
    val rates_scores_stats: List<*>, // TODO: Check
    val rates_statuses_stats: List<*>, // TODO: Check
    val updated_at: String,
    val next_episode_at: String?, // TODO: Check
    val fansubbers: List<*>, // TODO: Check
    val fundubbers: List<*>, // TODO: Check
    val licensors: List<*>, // TODO: Check
    val genres: List<*>, // TODO: Check
    val studios: List<*>, // TODO: Check
    val videos: List<*>, // TODO: Check
    val screenshots: List<*>, // TODO: Check
    val user_rate: Int?, // TODO: Check
) {
    data class Image(
        val original: String,
        val preview: String,
        val x96: String,
        val x48: String,
    )
}