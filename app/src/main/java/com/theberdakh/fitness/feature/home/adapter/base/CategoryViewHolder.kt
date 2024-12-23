package com.theberdakh.fitness.feature.home.adapter.base

import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.theberdakh.fitness.databinding.ItemCategoryBinding
import com.theberdakh.fitness.feature.home.model.ListItem

class CategoryViewHolder(private val binding: ItemCategoryBinding,
                         private val onViewAllClick: (String) -> Unit): BaseViewHolder(binding.root) {

    fun bind(item: ListItem.CategoryHeader) {
        binding.apply {
            tvName.text = item.name
            iconNext.setOnClickListener { onViewAllClick(item.name) }

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