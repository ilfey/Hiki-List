package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class TargetType {
    @SerializedName("Anime")
    ANIME,
    @SerializedName("Manga")
    MANGA,
}