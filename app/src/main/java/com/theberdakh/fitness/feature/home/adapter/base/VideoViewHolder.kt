package com.theberdakh.fitness.feature.home.adapter.base

import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.theberdakh.fitness.databinding.ItemVideoBinding
import com.theberdakh.fitness.feature.home.model.ListItem

class VideoViewHolder(private val binding: ItemVideoBinding,
                      private val onVideoClick: (String) -> Unit): BaseViewHolder(binding.root) {

    fun bind(item: ListItem.VideoItem) {
        binding.apply {
            tvTitle.text = item.name
            root.setOnClickListener { onVideoClick(item.name) }

            // Apply animation
            root.alpha = 0f
            root.animate()
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .start()
        }
    }
}