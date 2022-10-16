package com.web.testtask

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import com.web.testtask.di.Koin
import timber.log.Timber

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Koin.start(this)
    }
}
