package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class AnimeStatus {
    @SerializedName("anons")
    ANONS,

    @SerializedName("ongoing")
    ONGOING,

    @SerializedName("released")
    RELEASED,
}