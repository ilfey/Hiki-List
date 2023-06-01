package com.ilfey.shikimori.di.network.bodies

import com.google.gson.annotations.SerializedName
import com.ilfey.shikimori.di.network.enums.ListType

data class PatchUserRate(
    @SerializedName("user_rate")
    val userRate: UserRate
){
    data class UserRate(
        val chapters: Int? = null,
        val episodes: Int? = null,
        val rewatches: Int? = null,
        val score: Int? = null,
        val status: String? = null,
        val text: String? = null,
        val volumes: Int? = null,
    )

    companion object {
        fun newInstance(
            chapters: Int? = null,
            episodes: Int? = null,
            rewatches: Int? = null,
            score: Int? = null,
            status: ListType? = null,
            volumes: Int? = null,
        ) = PatchUserRate(
            UserRate(
                chapters = chapters,
                episodes = episodes,
                rewatches = rewatches,
                score = score,
                status = status?.value,
                volumes = volumes,
            )
        )

        fun newInstance(
            chapters: Int? = null,
            episodes: Int? = null,
            rewatches: Int? = null,
            score: Int? = null,
            statusString: String? = null,
            volumes: Int? = null,
        ) = PatchUserRate(
            UserRate(
                chapters = chapters,
                episodes = episodes,
                rewatches = rewatches,
                score = score,
                status = statusString,
                volumes = volumes,
            )
        )
    }
}
