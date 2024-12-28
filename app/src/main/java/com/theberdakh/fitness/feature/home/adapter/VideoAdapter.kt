package com.theberdakh.fitness.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.theberdakh.fitness.databinding.ItemVideoBinding
import com.theberdakh.fitness.feature.home.adapter.base.VideoViewHolder
import com.theberdakh.fitness.feature.home.model.ListItem

class VideoAdapter(private val onVideoClick: (ListItem.VideoItem) -> Unit): ListAdapter<ListItem.VideoItem, VideoViewHolder>(VideoItemCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onVideoClick
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object VideoItemCallback: DiffUtil.ItemCallback<ListItem.VideoItem>() {
    override fun areItemsTheSame(oldItem: ListItem.VideoItem, newItem: ListItem.VideoItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ListItem.VideoItem, newItem: ListItem.VideoItem): Boolean {
        return oldItem == newItem
    }

}