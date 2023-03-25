package com.ilfey.shikimori.di.network.models.parcelables

import android.os.Parcel
import android.os.Parcelable
import com.ilfey.shikimori.di.network.models.Anime

class ParcelableScores(
    val scores: List<Anime.RatesScoresStats>,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        List(parcel.readInt()) { parcel.readScoresStats() }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(scores.size)
        for (score in scores) {
            score.writeToParcel(parcel)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ParcelableScores> {
        override fun createFromParcel(parcel: Parcel): ParcelableScores {
            return ParcelableScores(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableScores?> {
            return arrayOfNulls(size)
        }
    }
}