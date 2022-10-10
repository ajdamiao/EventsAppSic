package com.example.appevents

import android.app.Application
import androidx.multidex.MultiDex
import com.example.appevents.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EventsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@EventsApp)

            modules(mainModule)
        }
    }
}