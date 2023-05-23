package com.ilfey.shikimori.di.network.models.parcelables

import android.os.Parcel
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.models.Anime

fun Anime.RatesStatusesStats.writeToParcel(out: Parcel) {
    out.writeString(name.name)
    out.writeInt(value)
}

fun Parcel.readStatusesStats() = Anime.RatesStatusesStats(
    name = ListType.valueOf(requireNotNull(readString())),
    value = readInt(),
)

fun Anime.RatesScoresStats.writeToParcel(out: Parcel) {
    out.writeInt(name)
    out.writeInt(value)
}

fun Parcel.readScoresStats() = Anime.RatesScoresStats(
    name = readInt(),
    value = readInt(),
)
