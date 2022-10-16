package com.web.testtask.data.model


data class Weather(
    val base: String,
    val cod: Int,
    val id: Int,
    val main: Main,
    val weather: List<WeatherX>,
    val wind: Wind,
    val name: String
)