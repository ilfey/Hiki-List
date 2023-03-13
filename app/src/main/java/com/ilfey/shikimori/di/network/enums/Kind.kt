package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Kind {
    @SerializedName("tv")
    TV,
    @SerializedName("movie")
    MOVIE,
    @SerializedName("ova")
    OVA,
    @SerializedName("ona")
    ONA,
    @SerializedName("special")
    SPECIAL,
    @SerializedName("music")
    MUSIC,
    @SerializedName("tv_13")
    TV_13,
    @SerializedName("tv_24")
    TV_24,
    @SerializedName("tv_48")
    TV_48,

    @SerializedName("manga")
    MANGA,
    @SerializedName("manhwa")
    MANHWA,
    @SerializedName("manhua")
    MANHUA,
    @SerializedName("light_novel")
    LIGHT_NOVEL,
    @SerializedName("novel")
    NOVEL,
    @SerializedName("one_shot")
    ONE_SHOT,
    @SerializedName("doujin")
    DOUJIN,
}