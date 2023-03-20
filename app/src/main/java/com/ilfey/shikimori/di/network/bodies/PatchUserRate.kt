package com.ilfey.shikimori.di.network.bodies

import com.ilfey.shikimori.di.network.enums.ListTypes

data class PatchUserRate(
    val user_rate: UserRate
){
    data class UserRate(
        val chapters: Int? = null,
        val episodes: Int? = null,
        val rewatches: Int? = null,
        val score: Int? = null,
        val status: ListTypes? = null,
        val text: String? = null,
        val volumes: Int? = null,
    )
}
