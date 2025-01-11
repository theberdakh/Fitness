package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.domain.model.SubscriptionPack
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem

fun NetworkPack.toDomain() = SubscriptionPack(
    id = id,
    name = title,
    price = price.toString(),
)


fun SubscriptionPack.toSubscriptionPackItem() = SubscriptionPackItem(
    name = name,
    price = price,
    content = ""
)

fun List<SubscriptionPack>.toSubscriptionPackItems() = this.map { it.toSubscriptionPackItem() }


