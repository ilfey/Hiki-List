package com.ilfey.shikimori.di.network

import com.ilfey.shikimori.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthorizationInterceptor(
    private val storage: Storage,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .addUserAgentHeader()
            .addTokenHeader()
            .let { chain.proceed(it) }
    }

    private fun Request.addUserAgentHeader(): Request {
        return newBuilder().apply {
            header("User-Agent", BuildConfig.APP_NAME)
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