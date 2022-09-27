package com.web.testtask.di.network

import androidx.paging.PagingSource
import com.web.testtask.data.model.DataModel
import com.web.testtask.remote.GifService

class GifPagingSource(
    private val photoApiService: GifService,
    private val source: PagingSource<Int, DataModel>,
    private val online: Boolean,
    private val isOnline: (Boolean) -> Boolean
) : BasePagingSource() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataModel> {
        return try {
            val nextPage = params.key ?: 0
            val response = photoApiService.fetchGifs(offset = nextPage)

            val listItem = blackList(response)

            isOnline(online)
            if (online)
                loadResult(listItem, nextPage)
            else
                source.load(params)
        } catch (e: Exception) {
            isOnline(false)
            source.load(params)
        }
    }
}