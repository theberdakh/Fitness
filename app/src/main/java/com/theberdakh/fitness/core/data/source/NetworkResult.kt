package com.theberdakh.fitness.core.data.source


sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}

/**
 * Simple wrapper to make request and wrap to NetworkResult
 * */
suspend fun <T> makeRequest(apiCall: suspend () -> T): NetworkResult<T> = try {
    apiCall()
    NetworkResult.Success(apiCall())
} catch (e: Exception) {
    NetworkResult.Error(e)
}