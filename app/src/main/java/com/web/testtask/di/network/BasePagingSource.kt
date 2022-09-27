package com.web.testtask.di.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.web.testtask.AppDelegate.Companion.API_PAGE_SIZE
import com.web.testtask.data.model.DataModel
import com.web.testtask.data.model.Gif
import com.web.testtask.util.getSharedPref

abstract class BasePagingSource : PagingSource<Int, DataModel>() {
    fun blackList(response: Gif): List<DataModel> {
        val blackList = getSharedPref()
        return response.data.filter {
            !(blackList?.contains(it.id) ?: false)
        }
    }

    fun loadResult(listItem: List<DataModel>, nextPage: Int) =
        LoadResult.Page(
            data = listItem,
            prevKey = if (nextPage == 0) null else nextPage - 1,
            nextKey = nextPage + API_PAGE_SIZE
        )

    override fun getRefreshKey(state: PagingState<Int, DataModel>): Int? {
        return null
    }
}