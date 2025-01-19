package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionOrderStatus

data class NetworkOrder(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("pack_id")
    val packId: Int,
    val pack: NetworkPack,
    val amount: Float,
    val status: String,
    val payments: NetworkPayments,
    @SerializedName("created_at")
    val createdAt: String
)

data class NetworkPayments(
    @SerializedName("payme_url")
    val paymeUrl: String
)

data class NetworkPack(
    val id: Int,
    val title: String,
    val price: Float
)


fun NetworkOrder.toDomain() = SubscriptionOrder(
    id = id,
    pack = pack.toDomain(),
    amount = amount.toString(),
    status = when (status.lowercase()) {
        "success" -> SubscriptionOrderStatus.SUBSCRIBED
        "new" -> SubscriptionOrderStatus.NEW
        "finished" -> SubscriptionOrderStatus.FINISHED
        else -> SubscriptionOrderStatus.UNDEFINED // Default value for unknown statuses
    },
    paymentUrl = payments.paymeUrl,
    createdAt = createdAt
)