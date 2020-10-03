package com.samokatnn.lamboscooter.managers.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

open class Coroutine(backgroundContext: CoroutineContext = Dispatchers.IO) {

    companion object {
        val TAG = Coroutine::class.java.simpleName
    }

    protected var mBackgroundContext: CoroutineContext = backgroundContext
    protected var mForegroundContext: CoroutineContext = Dispatchers.Main
    protected var mJob = SupervisorJob()
    protected var mScope = CoroutineScope(mJob + mForegroundContext)


    protected open fun onError(e: Exception) {
        // Stub
    }

    protected open fun <T> onComplete(result: T?) {
        // Stub
    }

    protected open fun <T> onBackground(): T? {
        return null
    }

    fun <T> run(onError: ((Exception) -> Unit)? = null,
                onComplete: ((T?) -> Unit)? = null,
                onBackground: (suspend CoroutineScope.(foreground: CoroutineScope) -> T?)? = null
    ): Job {
        return mScope.launch(mForegroundContext) main@ {
            try {
                val result = withContext(mBackgroundContext) {
                    onBackground?.invoke(this, this@main) ?: onBackground<T>()
                }
                onComplete(result)
                onComplete?.invoke(result)
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    onError(e)
                    onError?.invoke(e)
                }
            }
        }
    }

    fun cancel() {
        mJob.cancelChildren()
    }

}

suspend fun <T> wrapper(block: (onResult: (T?) -> Unit) -> Unit): T? {
    return suspendCancellableCoroutine { continuation ->
        try {
            block { result ->
                continuation.takeUnless { it.isCompleted }?.resume(result)
            }
        } catch (e: Exception) {
            continuation.takeUnless { it.isCompleted }?.resumeWithException(e)
        }
    }
}