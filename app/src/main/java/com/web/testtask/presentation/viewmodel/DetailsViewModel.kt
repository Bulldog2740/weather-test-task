package com.web.testtask.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.web.testtask.core.CoroutineViewModel
import com.web.testtask.data.model.Coord
import com.web.testtask.data.model.Weather
import com.web.testtask.data.repository.WeatherRepository
import com.web.testtask.util.NetworkResult
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    private val repository: WeatherRepository,
) : CoroutineViewModel() {
    private val _weather = MutableLiveData<NetworkResult<Weather>>()
    val weatherResponse: LiveData<NetworkResult<Weather>> = _weather

    fun getWeather(coord: Coord) = viewModelScope.launch {
        repository.getWeather(coord.lon, coord.lat).collect {
            _weather.value = it
        }
    }
}