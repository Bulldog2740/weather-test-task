package com.web.testtask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.web.testtask.data.database.AppDatabase
import com.web.testtask.data.database.GifDao
import com.web.testtask.data.model.DataModel
import com.web.testtask.di.network.GifPagingSource
import com.web.testtask.di.network.GifPagingSourceSearch
import com.web.testtask.remote.GifService
import kotlinx.coroutines.CoroutineScope

class GifRepository constructor(
    private val gifService: GifService,
) {
    private val gifDao: GifDao = AppDatabase.instance!!.gifDao

    suspend fun insertGif(data: DataModel) = gifDao.insertData(data)

    suspend fun deleteGif(data: DataModel) = gifDao.deleteData(data)

    private fun getAllGifsDB() = gifDao.getAllGifsPagingSource()

    fun getAllGifs(scope: CoroutineScope, isOnline: Boolean, onOffline: (Boolean) -> Unit) =
        Pager(PagingConfig(pageSize = 20)) {
            GifPagingSource(
                gifService,
                getAllGifsDB(),
                isOnline
            ) {
                onOffline(it)
                isOnline
            }
        }.flow.cachedIn(scope)

    fun getSearched(scope: CoroutineScope, text: String) = Pager(PagingConfig(pageSize = 20)) {
        GifPagingSourceSearch(gifService, text)
    }.flow.cachedIn(scope)
}