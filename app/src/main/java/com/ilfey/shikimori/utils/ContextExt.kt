package com.ilfey.shikimori.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.ilfey.shikimori.R

fun Context.toast(@StringRes id: Int = R.string.functionality_not_implemented_yet) {
    Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
}

fun Context.toast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}

@ColorInt
fun Context.color(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.dp(dp: Int): Int {
    return (resources.displayMetrics.density * dp).toInt()
}