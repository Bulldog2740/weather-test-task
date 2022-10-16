package com.web.testtask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel
import com.web.testtask.presentation.viewmodel.SearchedCitiesPagingSource
import com.web.testtask.remote.WeatherService
import com.web.testtask.util.BaseApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepository constructor(
    private val weatherService: WeatherService,
    private val dao: WeatherDao
) : BaseApiResponse() {

    suspend fun getWeather(lon: Double, lat: Double) = flow {
        emit(safeApiCall { weatherService.fetchWeather(lon, lat) })
    }.flowOn(Dispatchers.IO)


    fun getAllCities(): Flow<PagingData<CityModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { dao.getCities() }
        ).flow
    }

    fun getSearchedCities(newText: String) =
        Pager(PagingConfig(20)) { SearchedCitiesPagingSource(newText, dao) }
            .flow
            .flowOn(Dispatchers.IO)
}
