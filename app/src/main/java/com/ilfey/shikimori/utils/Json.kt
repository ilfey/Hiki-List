package com.ilfey.shikimori.utils

import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.closeQuietly
import org.json.JSONArray
import org.json.JSONObject

fun Response.parseJson(): JSONObject = try {
    JSONObject(requireBody().string())
} finally {
    closeQuietly()
}

fun Response.parseJsonArray(): JSONArray = try {
    JSONArray(requireBody().string())
} finally {
    closeQuietly()
}

private fun Response.requireBody(): ResponseBody = requireNotNull(body) { "Response body is null" }