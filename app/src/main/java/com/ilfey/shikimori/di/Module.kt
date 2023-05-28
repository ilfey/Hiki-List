package com.ilfey.shikimori.di

import com.google.gson.GsonBuilder
import com.ilfey.shikimori.BuildConfig
import com.ilfey.shikimori.di.network.*
import com.ilfey.shikimori.di.network.apis.*
import com.ilfey.shikimori.di.network.services.AnimeService
import com.ilfey.shikimori.di.network.services.UserRateService
import com.ilfey.shikimori.di.network.services.UserService
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd")
    .create()


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
            Retrofit.Builder()
                .client(get())
                .baseUrl("${BuildConfig.APP_URL}/")
                .addConverterFactory(
                    GsonConverterFactory.create(gson)
                )
                .build()
        }

        single {
            val retrofit = get<Retrofit>()

            val animeApi = retrofit.create(AnimeApi::class.java)

            AnimeService(animeApi, settings = get(), context = get())
        }

        single {
            val retrofit = get<Retrofit>()

            val userApi = retrofit.create(UserApi::class.java)

            UserService(userApi, settings = get(), context = get())
        }

        single {
            val retrofit = get<Retrofit>()

            val userRateApi = retrofit.create(UserRateApi::class.java)

            UserRateService(userRateApi, settings = get(), context = get())
        }
    }