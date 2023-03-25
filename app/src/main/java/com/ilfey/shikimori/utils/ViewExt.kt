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

/**
 * check visible
 * */
fun View.isVisible(callback: ((View) -> Unit)? = null) =
    if (visibility == View.VISIBLE) {
        callback?.invoke(this)
        true
    } else {
        false
    }

/**
 * check gone
 * */
fun View.isGone(callback: ((View) -> Unit)? = null) =
    if (visibility == View.VISIBLE) {
        callback?.invoke(this)
        true
    } else {
        false
    }

/**
 * check invisible
 * */
fun View.isInvisible(callback: ((View) -> Unit)? = null) =
    if (visibility == View.VISIBLE) {
        callback?.invoke(this)
        true
    } else {
        false
    }