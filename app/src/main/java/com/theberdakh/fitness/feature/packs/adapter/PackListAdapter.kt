package com.theberdakh.fitness.feature.packs.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.feature.packs.model.PackListItem
import com.theberdakh.fitness.feature.packs.model.PackListItemDiffCallback

class PackListAdapter: ListAdapter<PackListItem, PackListItemViewHolder>(PackListItemDiffCallback) {

    private var onPackClickListener: ((PackListItem.PackItemUnsubscribed) -> Unit)? = null
    fun setOnPackClickListener(listener: (PackListItem.PackItemUnsubscribed) -> Unit) {
        onPackClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackListItemViewHolder {
        return PackListItemViewHolder.create(viewType, parent, onPackClickListener = onPackClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is PackListItem.PackHeader -> PackListItem.VIEW_TYPE_HEADER
            is PackListItem.PackItemUnsubscribed -> PackListItem.VIEW_TYPE_ITEM
            is PackListItem.Loading -> PackListItem.VIEW_TYPE_LOADING
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: PackListItemViewHolder, position: Int) {
        when(holder) {
            is PackListItemViewHolder.PackHeaderViewHolder -> holder.bind(getItem(position) as PackListItem.PackHeader)
            is PackListItemViewHolder.PackItemViewHolder -> holder.bind(getItem(position) as PackListItem.PackItemUnsubscribed)
            is PackListItemViewHolder.PackItemLoadingViewHolder -> holder.bind()
        }
    }
}

