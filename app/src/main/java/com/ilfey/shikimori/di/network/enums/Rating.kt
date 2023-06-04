package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Rating(val value: String) {
    @SerializedName("none")
    NONE("None"),
    @SerializedName("g", alternate = ["G"])
    G("G"),
    @SerializedName("pg", alternate = ["PG"])
    PG("PG"),
    @SerializedName("pg_13", alternate = ["PG-13"])
    PG_13("PG-13"),
    @SerializedName("r", alternate = ["R"])
    R("R"),
    @SerializedName("r_plus", alternate = ["R+"])
    R_PLUS("R+"),
    @SerializedName("rx", alternate = ["Rx"])
    RX("Rx"),
}