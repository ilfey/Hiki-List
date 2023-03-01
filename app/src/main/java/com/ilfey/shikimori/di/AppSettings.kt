package com.ilfey.shikimori.di

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.ilfey.shikimori.R

class AppSettings(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var isVisited: Boolean
        get() = prefs.getBoolean(KEY_VISITED, false)
        set(value) = prefs.edit { putBoolean(KEY_VISITED, value) }

    var theme: Int
        get() = prefs.getInt(KEY_THEME, R.style.AppTheme_Light)
        set(value) = prefs.edit { putInt(KEY_THEME, value) }

    companion object {
        const val KEY_VISITED = "visited"
        const val KEY_THEME = "theme"
    }
}