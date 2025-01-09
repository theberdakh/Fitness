package com.theberdakh.fitness.core.data.source

import kotlinx.serialization.Serializable


sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : NetworkResult<Nothing>()
}

/**
 * Error model of server's response error to display error message to user in UI
 *
 * */
@Serializable
data class NetworkServerError(
    val message: String,
    val errors: Map<String, List<String>>
)


