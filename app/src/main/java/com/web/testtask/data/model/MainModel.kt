package com.web.testtask.data.model

import com.google.gson.annotations.SerializedName

data class MainModel(
    val humidity: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)