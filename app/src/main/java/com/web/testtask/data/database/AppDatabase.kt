package com.web.testtask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel

@Database(entities = [CityModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}
