package com.web.testtask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.di.Koin
import timber.log.Timber

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // Init DI
        Koin.start(this)
        sharedPref = getSharedPreferences("node_list_pref", Context.MODE_PRIVATE)
        AppDatabase.init(applicationContext)

    }

    companion object {
        var sharedPref: SharedPreferences? = null
        const val GIF  = ".gif"
        const val NEW_PREF_KEY: String = "node_list"
        const val API_LINK = "https://api.giphy.com"
        const val API_PAGE_SIZE = 20
    }
}
