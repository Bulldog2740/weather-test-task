package com.web.testtask.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web.testtask.di.AppDispatchers
import com.web.testtask.core.api.BaseApiResponse
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel
import com.web.testtask.core.SearchedCitiesPagingSource
import com.web.testtask.remote.WeatherService
import com.web.testtask.util.oneTimeCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class WeatherRepository constructor(
    private val weatherService: WeatherService,
    private val dao: WeatherDao,
    private val context:Context,
    private val ioDispatcher: AppDispatchers
) : BaseApiResponse() {

    private fun readJSONFromAssets() =
        context.assets
            ?.open("city_list.json")
            ?.reader()

    private suspend fun prePopulateDatabase(climbingRouteDao: WeatherDao) {
        val listType = object : TypeToken<List<CityModel>>() {}.type
        val city = Gson().fromJson<List<CityModel>>(readJSONFromAssets(), listType)
        climbingRouteDao.insertAll(city)
    }
    init {
        oneTimeCoroutineScope(ioDispatcher.io).launch {
            prePopulateDatabase(dao)
        }
    }

    suspend fun getWeather(lon: Double, lat: Double) = flow {
        emit(safeApiCall { weatherService.fetchWeather(lon, lat) })
    }.flowOn(ioDispatcher.io)


    fun getAllCities(): Flow<PagingData<CityModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { dao.getAllCities() }
        ).flow
    }

    fun getSearchedCities(newText: String) =
        Pager(PagingConfig(20)) { SearchedCitiesPagingSource(newText, dao) }
            .flow
            .flowOn(ioDispatcher.io)
}
