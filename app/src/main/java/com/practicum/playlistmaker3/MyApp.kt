package com.practicum.playlistmaker3

import android.app.Application
import com.practicum.playlistmaker3.di.dataModule
import com.practicum.playlistmaker3.di.iteractorModule
import com.practicum.playlistmaker3.di.repositoryModule
import com.practicum.playlistmaker3.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(dataModule, repositoryModule, iteractorModule, viewModelModule)
        }
    }
}