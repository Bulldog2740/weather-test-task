package com.web.testtask.di

import com.web.testtask.presentation.viewmodel.DetailsViewModel
import com.web.testtask.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        MainViewModel(
            repository = get()
        )
    }
    viewModel {
        DetailsViewModel(
            repository = get()
        )
    }
}
