package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class ListTypes(val value: String) {
    @SerializedName("planned")
    PLANNED("planned"),
    @SerializedName("watching")
    WATCHING("watching"),
    @SerializedName("rewatching")
    REWATCHING("rewatching"),
    @SerializedName("completed")
    COMPLETED("completed"),
    @SerializedName("on_hold")
    ON_HOLD("on_hold"),
    @SerializedName("dropped")
    DROPPED("dropped"),
}
