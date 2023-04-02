package com.ilfey.shikimori.di

import android.content.Context
import androidx.annotation.StyleRes
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.Storage
import com.ilfey.shikimori.di.network.enums.ListTypes

class AppSettings(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var isVisited: Boolean
        get() = prefs.getBoolean(KEY_VISITED, false)
        set(value) = prefs.edit { putBoolean(KEY_VISITED, value) }

    var isAuthorized: Boolean
        get() = prefs.getBoolean(KEY_AUTHORIZED, false)
        set(value) = prefs.edit { putBoolean(KEY_AUTHORIZED, value) }

    var list: ListTypes?
        get() = prefs.getString(KEY_LIST, null)?.let { ListTypes.valueOf(it) }
        set(value) = prefs.edit { putString(KEY_LIST, value?.name) }

    var fragment: Int
        get() = prefs.getInt(KEY_FRAGMENT, R.id.nav_lists)
        set(value) = prefs.edit { putInt(KEY_FRAGMENT, value) }

    var userId: Long
        get() = prefs.getLong(KEY_USER_ID, 0)
        set(value) = prefs.edit { putLong(KEY_USER_ID, value) }

    var theme: Int
        get() = prefs.getInt(KEY_THEME, R.style.AppTheme_Light)
        set(value) = prefs.edit { putInt(KEY_THEME, value) }

    var isNsfwEnable: Boolean
        get() = prefs.getBoolean(KEY_NSFW, false)
        set(value) = prefs.edit { putBoolean(KEY_NSFW, value) }

    var fullTitles: Boolean
        get() = prefs.getBoolean(KEY_FULL_TITLES, false)
        set(value) = prefs.edit { putBoolean(KEY_FULL_TITLES, value) }

    companion object {
        private const val KEY_AUTHORIZED = "authorized"
        private const val KEY_VISITED = "visited"
        private const val KEY_LIST = "list"
        private const val KEY_FRAGMENT = "fragment"
        private const val KEY_USER_ID = "user_id"

        const val KEY_THEME = "theme"
        const val KEY_NSFW = "nsfw"
        const val KEY_FULL_TITLES = "full_titles"
        const val KEY_APP_VERSION = "app_version"
    }
}