package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class ListTypes {
    @SerializedName("planned", alternate = ["Запланировано"])
    PLANNED,
    @SerializedName("watching", alternate = ["Смотрю"])
    WATCHING,
    @SerializedName("rewatching", alternate = ["Пересматриваю"])
    REWATCHING,
    @SerializedName("completed", alternate = ["Просмотрено"])
    COMPLETED,
    @SerializedName("on_hold", alternate = ["Отложено"])
    ON_HOLD,
    @SerializedName("dropped", alternate = ["Брошено"])
    DROPPED,
}
