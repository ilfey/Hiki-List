package com.ilfey.shikimori.di

import com.google.gson.GsonBuilder
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
                addInterceptor(
                    AuthorizationFailedInterceptor(
                        authenticator = get(),
                        storage = get(),
                    )
                )
                addInterceptor(AuthorizationInterceptor(storage = get()))
            }.build()
        }

        single {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create()

            Retrofit.Builder()
                .client(get())
                .baseUrl("${BuildConfig.APP_URL}/")
                .addConverterFactory(
                    GsonConverterFactory.create(gson)
                )
                .build()
                .create(ShikimoriRepository::class.java)
        }
    }