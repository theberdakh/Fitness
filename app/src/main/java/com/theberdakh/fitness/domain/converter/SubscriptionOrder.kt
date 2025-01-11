package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionOrderStatus
import com.theberdakh.fitness.domain.model.SubscriptionPack
import com.theberdakh.fitness.feature.packs.model.PackListItem

fun NetworkOrder.toDomain() = SubscriptionOrder(
    id = id,
    pack = pack.toDomain(),
    amount = amount.toString(),
    status = when (status.lowercase()) {
        "success" -> SubscriptionOrderStatus.SUBSCRIBED
        "new" -> SubscriptionOrderStatus.UNAVAILABLE
        "finished" -> SubscriptionOrderStatus.FINISHED
        else -> SubscriptionOrderStatus.UNDEFINED // Default value for unknown statuses
    },
    createdAt = createdAt
)

fun SubscriptionOrder.toPackListItem() =
    when (status) {
        SubscriptionOrderStatus.SUBSCRIBED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            orderId = this.id
        )

        SubscriptionOrderStatus.FINISHED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            orderId = this.id
        )

        SubscriptionOrderStatus.UNAVAILABLE -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            orderId = this.id
        )

        SubscriptionOrderStatus.UNDEFINED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            orderId = this.id
        )
    }

fun List<SubscriptionOrder>.toPackListItems() = this.map { it.toPackListItem() }