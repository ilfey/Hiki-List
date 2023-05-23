package com.ilfey.shikimori.di.network.enums

import com.google.gson.annotations.SerializedName

enum class MessageType {
    @SerializedName("inbox")
    INBOX,
    @SerializedName("private")
    PRIVATE,
    @SerializedName("sent")
    SENT,
    @SerializedName("news")
    NEWS,
    @SerializedName("notifications")
    NOTIFICATIONS,
}