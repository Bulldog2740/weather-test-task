package com.web.testtask.presentation.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.web.testtask.data.dao.WeatherDao
import com.web.testtask.data.model.CityModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchedCitiesPagingSource(private val text: String,private val userPostDao: WeatherDao) : PagingSource<Int, CityModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityModel> =
        withContext(Dispatchers.IO) {
            val randomPosts = userPostDao.getSearchedCities(text)
            randomPosts.load(params)
        }

    override fun getRefreshKey(state: PagingState<Int, CityModel>): Int? = null
}