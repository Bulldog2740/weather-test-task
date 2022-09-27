package com.web.testtask.di.network

import com.web.testtask.data.model.DataModel
import com.web.testtask.remote.GifService
import java.io.IOException

class GifPagingSourceSearch(
    private val photoApiService: GifService,
    private val text: String
) : BasePagingSource() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataModel> {
        return try {
            val nextPage = params.key ?: 0
            val response = photoApiService.fetchSearchGifs(offset = nextPage, text = text)

            val listItem = blackList(response)

            if (listItem.isEmpty() && nextPage > 0)
                LoadResult.Error(IOException())
            else
                loadResult(listItem, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}