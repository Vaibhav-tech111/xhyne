package com.xhyne.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object RateLimiter {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val executionMutex = Mutex()

    fun schedule(delayMillis: Long = 1500, action: suspend () -> Unit) {
        scope.launch {
            executionMutex.withLock {
                action()
                delay(delayMillis)
            }
        }
    }
}