package com.web.testtask.remote

import androidx.annotation.Keep
import com.web.testtask.AppDelegate.Companion.API_PAGE_SIZE
import com.web.testtask.BuildConfig
import com.web.testtask.data.model.Gif
import retrofit2.http.GET
import retrofit2.http.Query
@Keep
interface GifService {

    @GET("/v1/gifs/trending")
    suspend fun fetchGifs(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = API_PAGE_SIZE
    ): Gif

    @GET("/v1/gifs/search")
    suspend fun fetchSearchGifs(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = API_PAGE_SIZE,
        @Query("q") text: String
    ): Gif
}