package com.theberdakh.fitness.core.data.source.network.model


data class BaseNetworkModel<T>(
    val message: String,
    val data: T
)

