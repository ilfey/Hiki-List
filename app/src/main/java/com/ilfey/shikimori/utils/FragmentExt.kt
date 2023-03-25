package com.ilfey.shikimori.utils

import android.os.Bundle
import androidx.fragment.app.Fragment


inline fun <T : Fragment> T.withArgs(size: Int, block: Bundle.() -> Unit): T {
    val b = Bundle(size)
    b.block()
    this.arguments = b
    return this
}

fun Fragment.longArgument(name: String) = lazy(LazyThreadSafetyMode.NONE) {
    arguments?.getLong(name)
}

fun Fragment.intArgument(name: String) = lazy(LazyThreadSafetyMode.NONE) {
    arguments?.getInt(name)
}

fun Fragment.stringArrayArgument(name: String) = lazy(LazyThreadSafetyMode.NONE) {
    arguments?.getStringArray(name)
}