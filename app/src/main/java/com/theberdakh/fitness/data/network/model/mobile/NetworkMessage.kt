package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkMessage(
    val id: Int,
    val text: String,
    @SerializedName("is_sender_user")
    val isSenderUser: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)

fun NetworkMessage.toDomain() = com.theberdakh.fitness.domain.model.Message(
    id = id,
    text = text,
    isMyMessage = isSenderUser,
    time = createdAt
)
