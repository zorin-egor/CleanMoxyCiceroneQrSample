package com.samokatnn.lamboscooter.managers.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Coroutine(backgroundContext: CoroutineContext = Dispatchers.Default) {

    protected var mBackgroundContext: CoroutineContext = Dispatchers.Default
    protected var mForegroundContext: CoroutineContext = Dispatchers.Main
    protected var mScope = MainScope()

    init {
        mBackgroundContext = backgroundContext
    }

    suspend fun <T> async(context: CoroutineContext = mBackgroundContext, block: suspend () -> T): Deferred<T> {
        return mScope.async(context) {
            block.invoke()
        }
    }

    fun <T> run(onBackground: suspend (foreground: CoroutineScope, instance: Coroutine) -> T, onComplete: ((T) -> Unit)? = null, onError: ((Throwable) -> Unit)? = null): Job {
        return mScope.launch(mForegroundContext) {
            try {
                val result = withContext(mBackgroundContext) {
                    onBackground(this@launch, this@Coroutine)
                }
                onComplete?.invoke(result)
            } catch (e: Throwable) {
                if (e !is CancellationException) {
                    onError?.invoke(e)
                }
            }
        }
    }

    fun cancel(): Boolean {
        return try {
            mScope.cancel()
            true
        } catch(e: Exception) {
            false
        } finally {
            mScope = MainScope()
        }
    }
}