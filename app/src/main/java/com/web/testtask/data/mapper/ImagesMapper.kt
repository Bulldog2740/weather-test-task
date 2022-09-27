package com.web.testtask.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web.testtask.data.model.Images

object ImagesMapper {

    private val gson: Gson = Gson()

    @TypeConverter
    fun fromJson(json: String): Images {
        return gson.fromJson(json, object : TypeToken<Images>() {}.type)
    }

    @TypeConverter
    fun toJson(sizes: Images): String {
        return gson.toJson(sizes)
    }
}