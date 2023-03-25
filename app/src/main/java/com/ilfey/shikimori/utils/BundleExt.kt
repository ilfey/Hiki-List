package com.ilfey.shikimori.utils

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.getParcelableCompat(key: String): T? {
    return getParcelable(key) as T?
}
