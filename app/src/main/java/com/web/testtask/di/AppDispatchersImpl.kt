package com.web.testtask.di

import com.web.testtask.domain.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatchersImpl(
    override val main: CoroutineDispatcher=Dispatchers.Main,
    override val io: CoroutineDispatcher=Dispatchers.IO,
    override val default: CoroutineDispatcher=Dispatchers.Default,
    override val unconfined: CoroutineDispatcher=Dispatchers.Unconfined
) : AppDispatchers {

}