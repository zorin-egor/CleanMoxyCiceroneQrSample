package com.samokatnn.lamboscooter.managers.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Coroutine(backgroundContext: CoroutineContext = Dispatchers.Default) {

    companion object {
        val TAG = Coroutine::class.java.simpleName
    }

    protected var mBackgroundContext: CoroutineContext = backgroundContext
    protected var mForegroundContext: CoroutineContext = Dispatchers.Main
    protected var mScope = MainScope()


    fun <T> run(onError: ((Exception) -> Unit)? = null,
                onComplete: ((T) -> Unit)? = null,
                onBackground: suspend Coroutine.(foreground: CoroutineScope) -> T
    ): Job {
        return mScope.launch(mForegroundContext) {
            try {
                val result = withContext(mBackgroundContext) {
                    onBackground(this@Coroutine, this@launch)
                }
                onComplete?.invoke(result)
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    onError?.invoke(e)
                }
            }
        }
    }

    suspend fun <T> async(context: CoroutineContext = mBackgroundContext,
                          block: suspend Coroutine.() -> T
    ): Deferred<T> {
        return mScope.async(context) {
            block(this@Coroutine)
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