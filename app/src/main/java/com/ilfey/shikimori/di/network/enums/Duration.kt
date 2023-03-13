package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Duration {
    /**
     * Less than 10 minutes
     * */
    @SerializedName("S") S,
    /**
     * Less than 30 minutes
     * */
    @SerializedName("D") D,
    /**
     * More than 30 minutes
     * */
    @SerializedName("F") F,
}