package com.ilfey.shikimori.di.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthorizationInterceptor(
    private val storage: Storage,
) : Interceptor {

    companion object {
        private const val USER_AGENT_SHIKIMORI = "ilfey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .addUserAgentHeader()
            .addTokenHeader()
            .let { chain.proceed(it) }
    }

    private fun Request.addUserAgentHeader(): Request {
        return newBuilder().apply {
            header("User-Agent", USER_AGENT_SHIKIMORI)
        }.build()
    }

    private fun Request.addTokenHeader(): Request {
        return newBuilder().apply {
            if (storage.accessToken != null) {
                header("Authorization", "Bearer ${storage.accessToken}")
            }
        }.build()
    }
}