package com.theberdakh.fitness.feature.free_lessons.vh

import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemLoadingBinding

class LoadingViewHolder(private val binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.progressBar.isIndeterminate = true
    }
}