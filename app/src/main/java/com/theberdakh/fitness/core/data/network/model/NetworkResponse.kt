package com.theberdakh.fitness.core.data.network.model

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T): NetworkResponse<T>()
    data class Error(val message: String): NetworkResponse<Nothing>()
    data object Loading: NetworkResponse<Nothing>()
}