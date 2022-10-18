package com.web.testtask.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.web.testtask.util.createCoroutineHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class CoroutineViewModel : ViewModel() {
    fun launchSafely(
        onError: ((Throwable?) -> Unit)? = null,
        onResult: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(createCoroutineHandler {
            onError?.invoke(it)?:it?.printStackTrace()
        }){
            onResult()
        }
    }
}