package com.ilfey.shikimori.di.network

import android.content.Context
import androidx.core.content.edit
import com.ilfey.shikimori.di.network.models.User
import org.jsoup.internal.StringUtil.StringJoiner

class Storage(context: Context) {
    private val prefs = context.getSharedPreferences("Shikimori", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString(KEY_ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_ACCESS_TOKEN, value) }

    var refreshToken: String?
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) = prefs.edit { putString(KEY_REFRESH_TOKEN, value) }

    fun clear() = prefs.edit {
        clear()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}