package com.ilfey.shikimori.app

import android.app.Application
import com.ilfey.shikimori.di.diModule
import com.ilfey.shikimori.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                diModule,
                uiModule
            )
        }
    }
}