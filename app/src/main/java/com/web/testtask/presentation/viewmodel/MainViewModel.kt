package com.web.testtask.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.web.testtask.data.model.CityModel
import com.web.testtask.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: WeatherRepository,
) : CoroutineViewModel() {

    private var lastSearch = ""

    val getAllCities = repository.getAllCities()

    private fun <T> debounce(
        waitMs: Long = 500L,
        scope: CoroutineScope,
        destinationFunction: (T) -> Unit
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(waitMs)
                destinationFunction(param)
            }
        }
    }

    val ld = MutableLiveData<PagingData<CityModel>>()

    val getCities = debounce<String>(scope = viewModelScope) { newText ->
        launchSafely {
            if (lastSearch != newText) {
                repository.getSearchedCities(newText)
                    .cachedIn(viewModelScope).collect {
                        ld.postValue(it)
                    }
            }
        }
        lastSearch = newText
    }
}