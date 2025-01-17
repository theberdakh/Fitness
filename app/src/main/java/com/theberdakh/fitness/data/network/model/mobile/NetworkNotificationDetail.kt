package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkNotificationDetail(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String
)

fun NetworkNotificationDetail.toDomain() = com.theberdakh.fitness.domain.model.Notification(
    id = id,
    title = title,
    description = description,
    date = createdAt
)
