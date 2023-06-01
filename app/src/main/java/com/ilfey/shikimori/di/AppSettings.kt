package com.ilfey.shikimori.di

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.enums.ListType

class AppSettings(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var isVisited: Boolean
        get() = prefs.getBoolean(KEY_VISITED, false)
        set(value) = prefs.edit { putBoolean(KEY_VISITED, value) }

    var isAuthorized: Boolean
        get() = prefs.getBoolean(KEY_AUTHORIZED, false)
        set(value) = prefs.edit { putBoolean(KEY_AUTHORIZED, value) }

    var list: ListType?
        get() = prefs.getString(KEY_LIST, null)?.let { ListType.valueOf(it) }
        set(value) = prefs.edit { putString(KEY_LIST, value?.name) }

    var fragment: Int
        get() = prefs.getInt(KEY_FRAGMENT, R.id.nav_lists)
        set(value) = prefs.edit { putInt(KEY_FRAGMENT, value) }

    var userId: Long
        get() = prefs.getLong(KEY_USER_ID, 0)
        set(value) = prefs.edit { putLong(KEY_USER_ID, value) }

    var username: String?
        get() = prefs.getString(KEY_USERNAME, null)
        set(value) = prefs.edit { putString(KEY_USERNAME, value) }

    var theme: Int
        get() = prefs.getInt(KEY_THEME, R.style.AppTheme_Light)
        set(value) = prefs.edit { putInt(KEY_THEME, value) }

    var isNsfwEnable: Boolean
        get() = prefs.getBoolean(KEY_NSFW, false)
        set(value) = prefs.edit { putBoolean(KEY_NSFW, value) }

    var fullTitles: Boolean
        get() = prefs.getBoolean(KEY_FULL_TITLES, false)
        set(value) = prefs.edit { putBoolean(KEY_FULL_TITLES, value) }

    var showIcons: Boolean
        get() = prefs.getBoolean(KEY_SHOW_ICONS, false)
        set(value) = prefs.edit { putBoolean(KEY_SHOW_ICONS, value) }

    var showActions: Boolean
        get() = prefs.getBoolean(KEY_SHOW_ACTIONS, true)
        set(value) = prefs.edit { putBoolean(KEY_SHOW_ACTIONS, value) }

    companion object {
        private const val KEY_AUTHORIZED = "authorized"
        private const val KEY_VISITED = "visited"
        private const val KEY_LIST = "list"
        private const val KEY_FRAGMENT = "fragment"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"

        const val KEY_THEME = "theme"
        const val KEY_NSFW = "nsfw"
        const val KEY_FULL_TITLES = "full_titles"
        const val KEY_SHOW_ICONS = "show_icons"
        const val KEY_SHOW_ACTIONS = "show_actions"
        const val KEY_APP_VERSION = "app_version"
    }
}