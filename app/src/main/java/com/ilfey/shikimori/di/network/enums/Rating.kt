package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Rating {
    @SerializedName("none")
    NONE,
    @SerializedName("g")
    G,
    @SerializedName("pg")
    PG,
    @SerializedName("pg_13")
    PG_13,
    @SerializedName("r")
    R,
    @SerializedName("r_plus")
    R_PLUS,
    @SerializedName("rx")
    RX,
}