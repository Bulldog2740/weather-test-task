package com.web.testtask.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.web.testtask.data.model.DataModel

@Dao
abstract class GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertData(data: DataModel)

    @Delete
    abstract suspend fun deleteData(data: DataModel)

    @Query("select * from gif_table order by id")
    abstract fun getAllGifsPagingSource(): PagingSource<Int, DataModel>

    @Query("select id from gif_table")
    abstract suspend fun getGifIds(): List<String>

}