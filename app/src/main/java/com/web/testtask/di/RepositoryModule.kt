package com.web.testtask.di

import com.web.testtask.data.repository.GifRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    factory { GifRepository(get()) }
}