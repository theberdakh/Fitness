package com.theberdakh.fitness.core.network.model.mobile

import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem

data class Pack(
    val id: Int,
    val title: String,
    val price: Float
)

fun Pack.toDomain() = SubscriptionPackItem(
    name = title,
    price = price.toString(),
    content = ""
)