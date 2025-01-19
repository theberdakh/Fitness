package com.theberdakh.fitness.feature.packs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.R
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.databinding.ItemCategoryBinding
import com.theberdakh.fitness.databinding.ItemLoadingBinding
import com.theberdakh.fitness.databinding.ItemSubscriptionPackBinding
import com.theberdakh.fitness.domain.model.SubscriptionOrderStatus
import com.theberdakh.fitness.feature.packs.model.PackListItem
import kotlin.jvm.Throws

sealed class PackListItemViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    class PackItemViewHolder private constructor(
        private val binding: ItemSubscriptionPackBinding,
        private val onPackClickListener: ((PackListItem.PackItemUnsubscribed) -> Unit)? = null
    ) : PackListItemViewHolder(binding.root) {
        fun bind(packItemUnsubscribed: PackListItem.PackItemUnsubscribed) {
            with(binding){
                tvPackPrice.isVisible = false
                tvPackName.text = packItemUnsubscribed.title
                btnBuy.text = when(packItemUnsubscribed.status){
                    SubscriptionOrderStatus.SUBSCRIBED -> root.context.getString(R.string.start)
                    SubscriptionOrderStatus.FINISHED -> root.context.getString(R.string.buy)
                    SubscriptionOrderStatus.NEW -> root.context.getString(R.string.buy)
                    SubscriptionOrderStatus.UNDEFINED -> root.context.getString(R.string.buy)
                }

                tvPackContent.text = packItemUnsubscribed.createdAt
                btnBuy.setOnClickListener {
                    onPackClickListener?.invoke(packItemUnsubscribed)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onPackClickListener: ((PackListItem.PackItemUnsubscribed) -> Unit)? = null
            ): PackItemViewHolder {
                val binding = ItemSubscriptionPackBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PackItemViewHolder(binding, onPackClickListener)
            }
        }
    }

    class PackItemLoadingViewHolder private constructor(
        private val binding: ItemLoadingBinding
    ) : PackListItemViewHolder(binding.root) {

        fun bind() {

        }

        companion object {
            fun from(parent: ViewGroup): PackItemLoadingViewHolder {
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PackItemLoadingViewHolder(binding)
            }
        }
    }

    class PackHeaderViewHolder private constructor(
        private val binding: ItemCategoryBinding
    ) : PackListItemViewHolder(binding.root) {

        fun bind(packHeader: PackListItem.PackHeader) {}

        companion object {
            fun from(parent: ViewGroup): PackHeaderViewHolder {
                val binding = ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PackHeaderViewHolder(binding)
            }
        }
    }

    companion object {

        @Throws(IllegalArgumentException::class)
        fun create(viewType: Int, parent: ViewGroup, onPackClickListener: ((PackListItem.PackItemUnsubscribed) -> Unit)? = null): PackListItemViewHolder {
            return when (viewType) {
                PackListItem.VIEW_TYPE_LOADING -> PackItemLoadingViewHolder.from(parent)
                PackListItem.VIEW_TYPE_HEADER -> PackHeaderViewHolder.from(parent)
                PackListItem.VIEW_TYPE_ITEM -> PackItemViewHolder.from(parent, onPackClickListener)
                else -> { throw IllegalArgumentException("Unknown view type {${this.TAG}") }
            }
        }
    }


}



