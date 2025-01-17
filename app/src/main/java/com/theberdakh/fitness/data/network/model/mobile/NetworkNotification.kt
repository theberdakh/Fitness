package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkNotification(
    val id: Int,
    val title: String,
    @SerializedName("is_viewed")
    val isViewed: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)

fun NetworkNotification.toDomain() = com.theberdakh.fitness.domain.model.Notification(
    id = id,
    title = title,
    date = createdAt
)
