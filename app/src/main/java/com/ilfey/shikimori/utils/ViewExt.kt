package com.ilfey.shikimori.utils

import android.view.View

/**
 * swap visibility
 * */
fun View.swap() {
    visibility = when (visibility) {
        View.GONE -> View.VISIBLE
        View.INVISIBLE -> View.VISIBLE
        View.VISIBLE -> View.GONE
        else -> visibility
    }
}

/**
 * set visibility gone
 * */
fun View.gone() {
    visibility = View.GONE
}

/**
 * set visibility invisible
 * */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * set visibility visible
 * */
fun View.visible() {
    visibility = View.VISIBLE
}