package com.ilfey.shikimori.utils

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import com.ilfey.shikimori.R

fun Toolbar?.addBackButton(@DrawableRes icon: Int = R.drawable.ic_arrow_back, listener: (() -> Unit)? = null) {
    this?.navigationIcon = this?.context?.drawable(icon)
    listener?.let { this?.setNavigationOnClickListener { listener.invoke() } }
}