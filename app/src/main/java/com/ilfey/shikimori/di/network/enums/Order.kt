package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class Order {
    /**
     * By id
     * */
    @SerializedName("id")
    ID,

    /**
     * By type
     * */
    @SerializedName("kind")
    KIND,

    /**
     * By popularity
     * */
    @SerializedName("popularity")
    POPULARITY,

    /**
     * By alphabetical order
     * */
    @SerializedName("name")
    NAME,

    /**
     * By release date
     * */
    @SerializedName("aired_on")
    AIRED_ON,

    /**
     * By number of episodes
     * */
    @SerializedName("episodes")
    EPISODES,

    /**
     * By by status
     * */
    @SerializedName("status")
    STATUS,

    /**
     * By random
     * */
    @SerializedName("random")
    RANDOM,

//    @SerializedName("id_desc") ID_DESC,
//    @SerializedName("ranked") RANKED,
//    @SerializedName("created_at") CREATED_AT,
//    @SerializedName("created_at_desc") CREATED_AT_DESC,
}