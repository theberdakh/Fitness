package com.theberdakh.fitness.feature.subscriptions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemSubscriptionPackBinding
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem

class SubscriptionPackItemAdapter :
    ListAdapter<SubscriptionPackItem, SubscriptionPackItemAdapter.ViewHolder>(
        SubscriptionPackItemDiffCallback
    ) {

    inner class ViewHolder(private val binding: ItemSubscriptionPackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubscriptionPackItem) {
            binding.apply {
                tvPackName.text = item.name
                tvPackPrice.text = item.price
                tvPackContent.text = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSubscriptionPackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object SubscriptionPackItemDiffCallback : DiffUtil.ItemCallback<SubscriptionPackItem>() {
    override fun areItemsTheSame(
        oldItem: SubscriptionPackItem,
        newItem: SubscriptionPackItem
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: SubscriptionPackItem,
        newItem: SubscriptionPackItem
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.price == newItem.price && oldItem.content == newItem.content
    }

}