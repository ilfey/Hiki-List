package com.ilfey.shikimori.di.network.models.parcelables

import android.os.Parcel
import android.os.Parcelable
import com.ilfey.shikimori.di.network.models.Anime

class ParcelableStatuses(
    val statuses: List<Anime.RatesStatusesStats>,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        List(parcel.readInt()) { parcel.readStatusesStats() }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(statuses.size)
        for (status in statuses) {
            status.writeToParcel(parcel)
        }
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ParcelableStatuses> {
        override fun createFromParcel(parcel: Parcel): ParcelableStatuses {
            return ParcelableStatuses(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableStatuses?> {
            return arrayOfNulls(size)
        }
    }
}