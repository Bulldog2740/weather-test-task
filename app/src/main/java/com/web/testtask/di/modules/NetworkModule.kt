package com.web.testtask.di

import android.content.Context
import androidx.databinding.ktx.BuildConfig
import com.web.testtask.di.network.AppGsonConvertorFactory
import com.web.testtask.di.network.MiddlewareInterceptor
import com.web.testtask.remote.WeatherService
import com.web.testtask.util.BASE_API_LINK
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

fun networkModule() = module {

    single {
        val okHttpClient = createOkHttpClient(androidApplication())
        val retrofitPB =
            getBaseRetrofit(okHttpClient, androidApplication(), BASE_API_LINK)
        retrofitPB.create(WeatherService::class.java)
    }
}

fun createOkHttpClient(context: Context): OkHttpClient.Builder {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        /*.authenticator(TokenRefreshAuthenticator(context))*/
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                /*.headers(getHeaders())*/
                .build()
            chain.proceed(request)
        })
}

fun getBaseRetrofit(okHttpClient: OkHttpClient.Builder, context: Context, url: String): Retrofit {

    val iterator = okHttpClient.interceptors().iterator()
    while (iterator.hasNext()) {
        val interceptor = iterator.next()
        if (interceptor is MiddlewareInterceptor)
            iterator.remove()
    }

    okHttpClient.addInterceptor(MiddlewareInterceptor())
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(AppGsonConvertorFactory.create(context))
        .baseUrl(url)
        .client(okHttpClient.build())
        .build()
}

