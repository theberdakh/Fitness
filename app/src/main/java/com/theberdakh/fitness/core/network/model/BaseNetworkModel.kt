package com.theberdakh.fitness.core.network.model


data class BaseNetworkModel<T>(
    val message: String,
    val data: T
)

