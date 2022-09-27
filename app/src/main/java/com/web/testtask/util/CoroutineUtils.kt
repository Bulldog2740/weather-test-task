package com.web.testtask.util

import android.app.Activity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface ExtendedCoroutineScope : CoroutineScope {
    /**
     * Method for attach coroutine scope.
     *
     * Call this method in:
     * 1. [Activity.onResume] after 'super' method invocation when you use [Activity]
     * 2. [Fragment.onViewCreated] after 'super' method invocation when you use [Fragment]
     * 3. [Presenter.attachView] after 'super' method invocation when you use [Presenter]
     */
    fun attachCoroutineScope()

    /**
     * Method for detach coroutine scope and cancel jobs.
     *
     * Call this method in:
     * 1. [Activity.onPause] before 'super' method invocation when you use [Activity]
     * 2. [Fragment.onDestroyView] before 'super' method invocation when you use [Fragment]
     * 3. [Presenter.detachView] before 'super' method invocation when you use [Presenter]
     */
    fun detachCoroutineScope()
}

class CancelableCoroutineScope(
    val className: String,
    val context: CoroutineContext = Dispatchers.Main.immediate
) : ExtendedCoroutineScope {

    private var job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = context + job + CoroutineName(className)

    override fun attachCoroutineScope() {
        if (job.isCancelled) {
            job = SupervisorJob()
        }
    }

    override fun detachCoroutineScope() {
        if (job.isCancelled.not())
            job.cancel()
    }
}

inline fun <reified K : Any> createCancelableCoroutine(init: CancelableCoroutineScope.() -> Unit): CancelableCoroutineScope {
    return CancelableCoroutineScope(K::class.java.simpleName).apply(init)
}

inline fun <reified K : Any> createCancelableCoroutine(context: CoroutineContext, init: CancelableCoroutineScope.() -> Unit): CancelableCoroutineScope {
    return CancelableCoroutineScope(K::class.java.simpleName, context).apply(init)
}

inline fun <reified K : Any> createCancelableCoroutine(): CancelableCoroutineScope {
    return createCancelableCoroutine<K> { }
}

inline fun <reified K : Any> createCancelableCoroutine(context: CoroutineContext): CancelableCoroutineScope {
    return createCancelableCoroutine<K>(context) { }
}

fun oneTimeCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate, init: CoroutineScope.() -> Unit): CoroutineScope {
    return CoroutineScope(context + Job()).apply(init)
}

fun oneTimeCoroutineScope(context: CoroutineContext = Dispatchers.Main.immediate): CoroutineScope {
    return oneTimeCoroutineScope(context) {}
}
