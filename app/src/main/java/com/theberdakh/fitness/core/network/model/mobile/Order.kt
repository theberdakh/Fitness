package com.theberdakh.fitness.core.network.model.mobile

import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.feature.packs.model.PackListItem

data class Order(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("pack_id")
    val packId: Int,
    val pack: Pack,
    val amount: Float,
    val status: String,
    @SerializedName("created_at")
    val createdAt: String
)
