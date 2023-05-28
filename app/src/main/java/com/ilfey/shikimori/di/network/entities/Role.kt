package com.ilfey.shikimori.di.network.entities

data class Role(
    val roles: List<String>,
    val roles_russian: List<String>,
    val character: CharacterOrPerson?,
    val person: CharacterOrPerson?,
) {
    data class CharacterOrPerson(
        val id: Long,
        val name: String,
        val russian: String,
        val image: Image,
        val url: String,
    ) {
        data class Image(
            val original: String,
            val preview: String,
            val x96: String,
            val x48: String,
        )
    }
}

fun List<Role>.characters() = filter { it.character != null }
fun List<Role>.person() = filter { it.person != null }
fun List<Role>.mainCharacters() = filter { "Main" in it.roles }
fun List<Role>.supportingCharacters() = filter { "Supporting" in it.roles }