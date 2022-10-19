package com.web.testtask.presentation.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.web.testtask.data.model.CoordinateModel
import com.web.testtask.data.model.Weather
import com.web.testtask.domain.repository.WeatherRepository
import com.web.testtask.domain.remote.NetworkResult
import com.web.testtask.presentation.viewmodel.CoroutineViewModel
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    private val repository: WeatherRepository,
) : CoroutineViewModel() {
    private val _weather = MutableLiveData<NetworkResult<Weather>>()
    val weatherResponse: LiveData<NetworkResult<Weather>> = _weather

    fun getWeather(coord: CoordinateModel) = viewModelScope.launch {
        repository.getWeather(coord.longitude, coord.latitude).collect {
            _weather.value = it
        }
    }
}