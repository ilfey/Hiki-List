package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.R
import com.ilfey.shikimori.di.network.entities.User
import com.ilfey.shikimori.di.network.enums.Locale
import java.text.SimpleDateFormat

data class CurrentUser(
    val id: Long,
    val username: String,
    val avatar: String,
    val image: String,
    val lastOnline: String,
    val url: String,
    val name: String?,
    val sex: String?,
    val website: String?,
    val birthOn: String?,
    val age: String?,
    val locale: Locale,
) {
    companion object {
        fun parseFromEntity(ctx: Context, e: User) : CurrentUser {
            val dateFormat = SimpleDateFormat(
                "dd MMMM yyyy",
                ctx.resources.configuration.locales.get(0),
            )

            return CurrentUser(
                id = e.id,
                username = e.nickname,
                avatar = e.avatar,
                image = e.image.x160,
                lastOnline = dateFormat.format(e.last_online_at),
                url = e.url,
                name = e.name,
                sex = e.sex.ifEmpty { null },
                website = e.website.ifEmpty { null },
                birthOn = e.birth_on?.let { dateFormat.format(it) },
                age = parseAge(e.full_years, ctx),
                locale = e.locale,
            )
        }

        private fun parseAge(age: Int?, context: Context) =
            if (age == null) {
                null
            } else if (age in 12..13) {
                context.getString(R.string.age_3, age)
            } else if (age % 10 == 1) {
                context.getString(R.string.age_1, age)
            } else if (age % 10 in 2..4) {
                context.getString(R.string.age_2, age)
            } else context.getString(R.string.age_3, age)
    }
}

