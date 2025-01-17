package com.theberdakh.fitness.feature.notification_details.model

import com.theberdakh.fitness.domain.model.Notification

fun Notification.toNotificationDetailsItem() = NotificationDetailsItem(
    id = id,
    title = title,
    description = description,
    date = date
)

data class NotificationDetailsItem(
    val id: Int,
    val title: String,
    val description: String,
    val date: String
)
