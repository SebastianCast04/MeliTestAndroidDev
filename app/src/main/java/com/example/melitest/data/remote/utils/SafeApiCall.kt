package com.example.melitest.data.remote.utils

import timber.log.Timber

suspend inline fun <T> safeApiCall(
    callName: String,
    crossinline block: suspend () -> T
): T {
    return try {
        block()
    } catch (t: Throwable) {
        val mapped = t.toNetworkError()
        Timber.e(t, "API call failed: %s -> %s", callName, "${mapped::class.simpleName}: ${mapped.message ?: ""}".trim())
        throw mapped
    }
}
