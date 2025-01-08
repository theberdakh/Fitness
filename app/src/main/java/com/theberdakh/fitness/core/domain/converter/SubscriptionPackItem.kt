package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem

fun NetworkPack.toSubscriptionPackItem() = SubscriptionPackItem(
    name = title,
    price = price.toString(),
    content = ""
)