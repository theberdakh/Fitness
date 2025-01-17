package com.theberdakh.fitness.feature.notification.model

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.fitness.domain.model.Notification

fun Notification.toNotificationItem() = NotificationItem(
    id = id,
    title = title,
    date = date)

fun List<Notification>.toNotificationItemList() = map { it.toNotificationItem() }

data class NotificationItem(
    val id: Int,
    val title: String,
    val date: String
)

object NotificationItemDiffCallback: DiffUtil.ItemCallback<NotificationItem>(){
    override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }
}
