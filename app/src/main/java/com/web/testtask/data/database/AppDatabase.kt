package com.web.testtask.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.web.testtask.data.mapper.ImagesMapper
import com.web.testtask.data.model.DataModel

@TypeConverters(ImagesMapper::class)
@Database(entities = [DataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gifDao: GifDao
    companion object {
        var instance: AppDatabase? = null
        fun init(context: Context) {
            instance = Room.databaseBuilder(
                context, AppDatabase::class.java, "db"
            ).build()
        }
    }
}