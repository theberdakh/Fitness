package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.feature.packs.model.OrderStatus
import com.theberdakh.fitness.feature.packs.model.PackListItem
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem

fun NetworkPack.toSubscriptionPackItem() = SubscriptionPackItem(
    name = title,
    price = price.toString(),
    content = ""
)

fun NetworkOrder.toPackListItem() = PackListItem.PackItemUnsubscribed(
    packId = this.id,
    title = this.pack.title,
    amount = this.amount,
    statusEnum = OrderStatus.fromString(this.status),
    createdAt = this.createdAt,
    orderId = this.id
)