package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Order(value: String) {
    /**
     * By id
     * */
    @SerializedName("id")
    ID("id"),

    /**
     * By type
     * */
    @SerializedName("kind")
    KIND("kind"),

    /**
     * By popularity
     * */
    @SerializedName("popularity")
    POPULARITY("popularity"),

    /**
     * By alphabetical order
     * */
    @SerializedName("name")
    NAME("name"),

    /**
     * By release date
     * */
    @SerializedName("aired_on")
    AIRED_ON("aired_on"),

    /**
     * By number of episodes
     * */
    @SerializedName("episodes")
    EPISODES("episodes"),

    /**
     * By by status
     * */
    @SerializedName("status")
    STATUS("status"),

    /**
     * By random
     * */
    @SerializedName("random")
    RANDOM("random"),

//    @SerializedName("id_desc") ID_DESC("id_desc"),
//    @SerializedName("ranked") RANKED("ranked"),
//    @SerializedName("created_at") CREATED_AT("created_at"),
//    @SerializedName("created_at_desc") CREATED_AT_DESC("created_at_desc"),
}