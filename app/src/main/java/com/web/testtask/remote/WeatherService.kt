package com.web.testtask.remote

import androidx.annotation.Keep
import com.web.testtask.data.model.Weather
import com.web.testtask.util.API_KEY
import com.web.testtask.util.UNITS_SYSTEM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
interface WeatherService {
    @GET("/data/2.5/weather")
    suspend fun fetchWeather(
        @Query("lon") lon: Double = 0.0,
        @Query("lat") lat: Double = 0.0,
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = UNITS_SYSTEM
    ): Response<Weather>
}