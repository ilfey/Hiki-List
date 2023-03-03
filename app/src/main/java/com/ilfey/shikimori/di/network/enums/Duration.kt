package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Duration(val value: String) {
    /**
     * Less than 10 minutes
     * */
    @SerializedName("S") S("S"),
    /**
     * Less than 30 minutes
     * */
    @SerializedName("D") D("D"),
    /**
     * More than 30 minutes
     * */
    @SerializedName("F") F("F"),
}