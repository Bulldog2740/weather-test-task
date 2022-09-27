package com.web.testtask.di

import androidx.annotation.Keep
import com.web.testtask.AppDelegate
import com.web.testtask.di.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

@Keep
object Koin {

    fun start(appDelegate: AppDelegate) {
        val koinApplication = GlobalContext.getOrNull()

        if (koinApplication != null) {
            // We already started KoinApplication
            return
        }

        val modules = listOf<Module>(
            viewModelModule(),
            networkModule(),
            repositoryModule()
        )

        startKoin {
            androidLogger(Level.NONE)
            androidContext(appDelegate)
            modules(modules)
        }
    }

    fun stop() {
        stopKoin()
    }
}