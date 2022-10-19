package com.web.testtask.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.web.testtask.core.SearchedCitiesPagingSource
import com.web.testtask.core.api.BaseApiResponse
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel
import com.web.testtask.domain.AppDispatchers
import com.web.testtask.domain.repository.WeatherRepository
import com.web.testtask.domain.remote.WeatherService
import com.web.testtask.util.oneTimeCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class WeatherRepositoryImpl constructor(
    override val weatherService: WeatherService,
    override val dao: WeatherDao,
    override val context: Context,
    override val ioDispatcher: AppDispatchers
) : BaseApiResponse(), WeatherRepository {

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

    override suspend fun getWeather(lon: Double, lat: Double) = flow {
        emit(safeApiCall { weatherService.fetchWeather(lon, lat) })
    }.flowOn(ioDispatcher.io)


    override fun getAllCities(): Flow<PagingData<CityModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { dao.getAllCities() }
        ).flow
    }

    override fun getSearchedCities(newText: String) =
        Pager(PagingConfig(20)) { SearchedCitiesPagingSource(newText, dao) }
            .flow
            .flowOn(ioDispatcher.io)
}
