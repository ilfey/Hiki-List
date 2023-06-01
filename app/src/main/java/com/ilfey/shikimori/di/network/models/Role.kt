package com.ilfey.shikimori.di.network.models

import android.content.Context
import com.ilfey.shikimori.di.network.entities.Role as eRole

data class Role(
    val roles: List<String>,
    val rolesRu: List<String>,
    val character: CharacterOrPerson?,
    val person: CharacterOrPerson?,
) {
    data class CharacterOrPerson(
        val id: Long,
        val nameEn: String,
        val nameRu: String,
        val image: String,
    )

    companion object {
        fun parseFromEntity(ctx: Context, e: eRole) : Role {
            return Role(
                roles = e.roles,
                rolesRu = e.roles_russian,
                character = if (e.character != null) parseCharacterOrPerson(e.character) else null,
                person = if (e.person != null) parseCharacterOrPerson(e.person) else null,
            )
        }

        private fun parseCharacterOrPerson(e: eRole.CharacterOrPerson) : CharacterOrPerson{
            return CharacterOrPerson(
                id = e.id,
                nameEn = e.name,
                nameRu = e.russian,
                image = makeUrl(e.image.original),
            )
        }
    }
}
