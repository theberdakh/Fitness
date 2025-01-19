package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionOrderStatus
import com.theberdakh.fitness.feature.packs.model.PackListItem


fun SubscriptionOrder.toPackListItem() =
    when (status) {
        SubscriptionOrderStatus.SUBSCRIBED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            paymentUrl = this.paymentUrl,
            orderId = this.id,
            status = this.status
        )

        SubscriptionOrderStatus.FINISHED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name + " (Законченный)",
            amount = this.amount,
            createdAt = this.createdAt,
            paymentUrl = this.paymentUrl,
            orderId = this.id,
            status = this.status
        )

        SubscriptionOrderStatus.NEW -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name + " (Новый)",
            amount = this.amount,
            createdAt = this.createdAt,
            paymentUrl = this.paymentUrl,
            orderId = this.id,
            status = this.status
        )

        SubscriptionOrderStatus.UNDEFINED -> PackListItem.PackItemUnsubscribed(
            packId = this.id,
            title = this.pack.name,
            amount = this.amount,
            createdAt = this.createdAt,
            paymentUrl = this.paymentUrl,
            orderId = this.id,
            status = this.status
        )
    }

fun List<SubscriptionOrder>.toPackListItems() = this.map { it.toPackListItem() }