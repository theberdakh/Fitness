package com.theberdakh.fitness.core.data.network.model


data class BaseNetworkModel<T>(
    val message: String,
    val data: T
)