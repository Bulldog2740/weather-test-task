package com.web.testtask.di

import androidx.room.Room
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun dataBaseModule() = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    factory { WeatherRepository(get(), get(),get(),get(named("IODispatcher"))) }
    single {
        Room.databaseBuilder(
            androidContext().applicationContext, AppDatabase::class.java, "db"
        ).build()
    }
    single { get<AppDatabase>().weatherDao }
}