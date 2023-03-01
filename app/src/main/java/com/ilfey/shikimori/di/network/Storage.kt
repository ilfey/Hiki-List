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

    var user: User?
        get() = prefs.getString(KEY_USER, null)?.let {
            val lines = it.lines()
            if (lines.size != 6) {
                return@let null
            }
            User(
                id = lines[0].toLong(),
                nickname = lines[1],
                avatar = lines[2],
                image = User.Image(
                    x160 = lines[3],
                    x148 = lines[4],
                    x80 = lines[5],
                    x64 = lines[6],
                    x48 = lines[7],
                    x32 = lines[8],
                    x16 = lines[9],
                ),
                url = lines[10],
                sex = lines[11],
                website = lines[12],
                last_online = lines[13],
                name = lines[14],
                full_years = lines[15].toInt(),
            )
        }
        set(value) = prefs.edit {
            if (value == null) {
                remove(KEY_USER)
                return@edit
            }
            val str = StringJoiner("\n")
                .add(value.id)
                .add(value.nickname)
                .add(value.avatar)
                .add(value.url)
                .add(value.sex)
                .add(value.website)
                .complete()
            putString(KEY_USER, str)
        }

    fun clear() = prefs.edit {
        clear()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER = "user"
    }
}