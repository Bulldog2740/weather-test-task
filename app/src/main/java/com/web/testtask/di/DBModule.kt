package com.web.testtask.di

import androidx.room.Room
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.data.repository.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun dataBaseModule() = module {

    factory { WeatherRepository(get(), get(),get()) }
    single {
        Room.databaseBuilder(
            androidContext().applicationContext, AppDatabase::class.java, "db"
        ).build()
    }
    single { get<AppDatabase>().weatherDao }

}