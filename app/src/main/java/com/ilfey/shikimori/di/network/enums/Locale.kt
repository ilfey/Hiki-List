package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Locale(val value: String) {
    @SerializedName("en")
    EN("en"),
    @SerializedName("ru")
    RU("ru"),
}