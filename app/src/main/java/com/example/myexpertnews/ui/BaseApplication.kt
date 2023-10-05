package com.example.myexpertnews.ui

import android.app.Application
import com.example.myexpertnews.ui.di.databaseModule
import com.example.myexpertnews.ui.di.networkModule
import com.example.myexpertnews.ui.di.repositoryModule
import com.example.myexpertnews.ui.di.useCaseModule
import com.example.myexpertnews.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}