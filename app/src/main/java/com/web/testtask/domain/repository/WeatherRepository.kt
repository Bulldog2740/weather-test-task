package com.web.testtask.domain.repository

import android.content.Context
import androidx.paging.PagingData
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel
import com.web.testtask.data.model.Weather
import com.web.testtask.domain.AppDispatchers
import com.web.testtask.domain.remote.NetworkResult
import com.web.testtask.domain.remote.WeatherService
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    val weatherService: WeatherService
    val dao: WeatherDao
    val context: Context
    val ioDispatcher: AppDispatchers

    suspend fun getWeather(lon: Double, lat: Double): Flow<NetworkResult<Weather>>
    fun getAllCities(): Flow<PagingData<CityModel>>
    fun getSearchedCities(newText: String): Flow<PagingData<CityModel>>
}
