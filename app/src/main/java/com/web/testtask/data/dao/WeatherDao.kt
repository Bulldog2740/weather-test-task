package com.web.testtask.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.web.testtask.data.model.CityModel


@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(list: List<CityModel>)

    @Query("SELECT * FROM city_table ")
    fun getCities(): PagingSource<Int, CityModel>

    @Query("SELECT * FROM city_table WHERE name LIKE '%' || :text  || '%'")
    fun getSearchedCities(text: String): PagingSource<Int, CityModel>
}
