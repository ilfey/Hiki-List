package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class AnimeStatus(val value: String) {
    @SerializedName("anons")
    ANONS("anons"),

    @SerializedName("ongoing")
    ONGOING("ongoing"),

    @SerializedName("ongoing")
    RELEASED("released"),
}