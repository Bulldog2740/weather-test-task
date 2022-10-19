package com.web.testtask.di.modules

import androidx.room.Room
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.data.repository.WeatherRepositoryImpl
import com.web.testtask.di.AppDispatchersImpl
import com.web.testtask.domain.repository.WeatherRepository
import com.web.testtask.domain.AppDispatchers
import com.web.testtask.util.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun dataBaseModule() = module {
    single<AppDispatchers>{ AppDispatchersImpl() }
    factory <WeatherRepository> {WeatherRepositoryImpl(get(), get(),get(),get())  }
    single {
        Room.databaseBuilder(
            androidContext().applicationContext, AppDatabase::class.java, DB_NAME
        ).build()
    }
    single { get<AppDatabase>().weatherDao }
}