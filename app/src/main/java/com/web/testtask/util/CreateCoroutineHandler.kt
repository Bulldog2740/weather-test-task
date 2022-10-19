package com.web.testtask.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

inline fun createCoroutineHandler(crossinline onError: (Throwable?) -> Unit) =
    CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

fun oneTimeCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate, init: CoroutineScope.() -> Unit): CoroutineScope {
    return CoroutineScope(context + Job()).apply(init)
}

fun oneTimeCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate): CoroutineScope {
    return oneTimeCoroutineScope(context) {}
}