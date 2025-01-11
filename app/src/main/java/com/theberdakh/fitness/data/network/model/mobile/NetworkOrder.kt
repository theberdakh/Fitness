package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkOrder(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("pack_id")
    val packId: Int,
    val pack: NetworkPack,
    val amount: Float,
    val status: String,
    @SerializedName("created_at")
    val createdAt: String
)

data class NetworkPack(
    val id: Int,
    val title: String,
    val price: Float
)