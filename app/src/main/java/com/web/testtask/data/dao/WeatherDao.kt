package com.web.testtask.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.web.testtask.data.model.CityModel

@Dao
interface WeatherDao {

    @Insert
    suspend fun insertAll(list: List<CityModel>)

    @Query("SELECT * FROM city_table ")
    fun getAllCities(): PagingSource<Int, CityModel>

    @Query("SELECT * FROM city_table WHERE name LIKE '%' || :text  || '%'")
    fun getCitiesByFilter(text: String): PagingSource<Int, CityModel>
}
