package com.theberdakh.fitness.feature.home.adapter.base

import com.theberdakh.fitness.databinding.ItemListBinding
import com.theberdakh.fitness.feature.home.adapter.VideoAdapter
import com.theberdakh.fitness.feature.home.model.ListItem

class VideoListViewHolder(
    private val binding: ItemListBinding,
    private val onVideoClick: (ListItem.VideoItem) -> Unit
) : BaseViewHolder(binding.root) {

    fun bind(item: ListItem.VideoList) {
        binding.apply {
            recyclerView.adapter = VideoAdapter(onVideoClick).apply {
                submitList(item.items)
            }
        }
    }
}