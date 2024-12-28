package com.theberdakh.fitness.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.databinding.ItemCategoryBinding
import com.theberdakh.fitness.databinding.ItemListBinding
import com.theberdakh.fitness.databinding.ItemVideoBinding
import com.theberdakh.fitness.feature.home.adapter.base.BaseViewHolder
import com.theberdakh.fitness.feature.home.adapter.base.CategoryViewHolder
import com.theberdakh.fitness.feature.home.adapter.base.VideoListViewHolder
import com.theberdakh.fitness.feature.home.adapter.base.VideoViewHolder
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.home.model.VideoListDiffCallback

class HomeAdapter(
    private val onVideoClick: (ListItem.VideoItem) -> Unit,
    private val onCategoryClick: (String) -> Unit
): ListAdapter<ListItem, BaseViewHolder>(VideoListDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is ListItem.VideoItem -> VIEW_TYPE_VIDEO
            is ListItem.VideoList -> VIEW_TYPE_VIDEO_LIST
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onCategoryClick
            )
            VIEW_TYPE_VIDEO -> VideoViewHolder(
                ItemVideoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onVideoClick
            )
            VIEW_TYPE_VIDEO_LIST -> VideoListViewHolder(
                ItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onVideoClick
            )
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.CategoryHeader -> (holder as CategoryViewHolder).bind(item)
            is ListItem.VideoItem -> (holder as VideoViewHolder).bind(item)
            is ListItem.VideoList -> (holder as VideoListViewHolder).bind(item)
        }
    }

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_VIDEO = 1
        private const val VIEW_TYPE_VIDEO_LIST = 2
    }
}