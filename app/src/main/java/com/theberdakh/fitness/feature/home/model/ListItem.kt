package com.theberdakh.fitness.feature.home.model

import androidx.recyclerview.widget.DiffUtil

sealed class ListItem {
    data class CategoryHeader(val name: String): ListItem()
    data class VideoItem(val id: Int, val name: String, val url: String = "", val module: String = ""): ListItem()
    data class VideoList(val items: List<VideoItem>): ListItem()
}

class VideoListDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return when {
            oldItem is ListItem.CategoryHeader && newItem is ListItem.CategoryHeader ->
                oldItem.name == newItem.name
            oldItem is ListItem.VideoItem && newItem is ListItem.VideoItem ->
                oldItem.name == newItem.name
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}

