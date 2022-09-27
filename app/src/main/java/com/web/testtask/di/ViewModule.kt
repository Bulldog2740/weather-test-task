package com.web.testtask.di

import com.web.testtask.presentation.viewmodel.GifViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        GifViewModel(
            repository = get()
        )
    }
}