package com.theberdakh.fitness.feature.packs.model

import androidx.recyclerview.widget.DiffUtil

enum class OrderStatus{
    SUCCESS, FINISHED, NEW, UNKNOWN;
    companion object {
        fun fromString(value: String): OrderStatus {
            return when(value){
                "success" -> SUCCESS
                "finished" -> FINISHED
                "new" -> NEW
                else -> UNKNOWN
            }
        }
    }
}

sealed class PackListItem {
    data class PackHeader(val title: String): PackListItem()
    data class PackItemUnsubscribed(
        val id: Int,
        val title: String,
        val amount: Float,
        val statusEnum: OrderStatus,
        val createdAt: String
    ): PackListItem() {
        companion object {
            fun statusEnum(status: String): OrderStatus {
                return OrderStatus.fromString(status)
            }
        }
    }
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
                oldItem.id == newItem.id
            oldItem is PackListItem.Loading && newItem is PackListItem.Loading -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: PackListItem, newItem: PackListItem): Boolean {
       return oldItem == newItem
    }
}