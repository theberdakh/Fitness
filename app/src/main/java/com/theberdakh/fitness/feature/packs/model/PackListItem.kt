package com.theberdakh.fitness.feature.packs.model

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.fitness.domain.model.SubscriptionOrderStatus

sealed class PackListItem {
    data class PackHeader(val title: String): PackListItem()
    data class PackItemUnsubscribed(
        val packId: Int,
        val title: String,
        val amount: String,
        val createdAt: String,
        val paymentUrl: String,
        val orderId: Int,
        val status: SubscriptionOrderStatus
    ): PackListItem()
    data object Loading: PackListItem()

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_LOADING = 2
    }
}

object PackListItemDiffCallback: DiffUtil.ItemCallback<PackListItem>() {
    override fun areItemsTheSame(oldItem: PackListItem, newItem: PackListItem): Boolean {
        return when {
            oldItem is PackListItem.PackHeader && newItem is PackListItem.PackHeader ->
                oldItem.title == newItem.title
            oldItem is PackListItem.PackItemUnsubscribed && newItem is PackListItem.PackItemUnsubscribed ->
                oldItem.packId == newItem.packId
            oldItem is PackListItem.Loading && newItem is PackListItem.Loading -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: PackListItem, newItem: PackListItem): Boolean {
       return oldItem == newItem
    }
}