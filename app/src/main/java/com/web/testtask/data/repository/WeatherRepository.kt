package com.web.testtask.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.data.model.CityModel
import com.web.testtask.presentation.viewmodel.SearchedCitiesPagingSource
import com.web.testtask.remote.WeatherService
import com.web.testtask.util.BaseApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext

class WeatherRepository constructor(
    private val weatherService: WeatherService,
    private val dao: WeatherDao,
    private val context:Context
) : BaseApiResponse() {

    fun readJSONFromAssets() =
        context.assets
            ?.open("city_list.json")
            ?.reader()

    suspend fun prePopulateDatabase(climbingRouteDao: WeatherDao) {
        val listType = object : TypeToken<List<CityModel>>() {}.type
        val city = Gson().fromJson<List<CityModel>>(readJSONFromAssets(), listType)
        climbingRouteDao.insert(city)
    }
    init {
        CoroutineScope(Dispatchers.IO).launch {
            prePopulateDatabase(dao)
        }
    }

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
