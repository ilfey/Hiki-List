package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class ListType(val value: String) {
    @SerializedName("planned", alternate = ["Запланировано"])
    PLANNED("planned"),
    @SerializedName("watching", alternate = ["Смотрю"])
    WATCHING("watching"),
    @SerializedName("rewatching", alternate = ["Пересматриваю"])
    REWATCHING("rewatching"),
    @SerializedName("completed", alternate = ["Просмотрено"])
    COMPLETED("completed"),
    @SerializedName("on_hold", alternate = ["Отложено"])
    ON_HOLD("on_hold"),
    @SerializedName("dropped", alternate = ["Брошено"])
    DROPPED("dropped"),
}
