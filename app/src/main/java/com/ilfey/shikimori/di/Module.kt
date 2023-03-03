package com.ilfey.shikimori.di

import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.di.network.*
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val diModule
    get() = module {
        single {
            AppSettings(context = get())
        }

        single {
            Storage(context = get())
        }

        single {
            Authenticator(storage = get(), authService = get())
        }

        single {
            AuthorizationService(get())
        }

        single {
           OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
                addNetworkInterceptor(AuthorizationInterceptor(storage = get()))
                addNetworkInterceptor(
                    AuthorizationFailedInterceptor(
                        authenticator = get(),
                        storage = get(),
                    )
                )
            }.build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl("https://shikimori.one/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ShikimoriRepository::class.java)
        }
    }