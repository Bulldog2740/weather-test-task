package com.web.testtask.di.modules

import com.web.testtask.presentation.fragments.details.DetailsViewModel
import com.web.testtask.presentation.fragments.main.MainViewModel
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
