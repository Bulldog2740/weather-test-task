package com.web.testtask.di.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AppGsonConvertorFactory(
    private val gson: Gson,
    private val context: Context
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return AppGsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return AppGsonRequestBodyConverter(gson, adapter)
    }

    companion object {
        fun create(gson: Gson?, context: Context): AppGsonConvertorFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return AppGsonConvertorFactory(gson, context)
        }

        fun create(context: Context): AppGsonConvertorFactory {
            return AppGsonConvertorFactory(Gson(), context)
        }
    }
}