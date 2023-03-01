package com.ilfey.shikimori.di.network.models

data class UserRate (
    val id : Long,
    val user_id : Long,
    val target_id : Long,
    val target_type : String,
    val score : Int,
    val status : String,
    val rewatches : Int,
    val episodes : Int,
    val volumes : Int,
    val chapters : Int,
    val text : String?,
    val text_html : String?,
    val created_at : String,
    val updated_at : String
)