package com.theberdakh.fitness.feature.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemNotificationBinding
import com.theberdakh.fitness.feature.notification.model.NotificationItem
import com.theberdakh.fitness.feature.notification.model.NotificationItemDiffCallback

class NotificationItemListAdapter :
    ListAdapter<NotificationItem, NotificationItemListAdapter.ViewHolder>(
        NotificationItemDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationItem) {
            binding.tvNotificationTitle.text = item.title
            binding.tvNotificationSubtitle.text = item.date
        }

    }
}