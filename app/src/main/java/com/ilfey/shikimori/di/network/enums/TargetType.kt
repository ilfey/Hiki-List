package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class TargetType(val value: String) {
    @SerializedName("Anime")
    ANIME("Anime"),
    @SerializedName("Manga")
    MANGA("Manga"),
}