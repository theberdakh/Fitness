package com.theberdakh.fitness.feature.free_lessons.vh

import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.core.network.model.mobile.Lesson
import com.theberdakh.fitness.databinding.ItemVideoHorizontalBinding
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.home.utils.YouTubeThumbnail

class FreeLessonViewHolder(private val binding: ItemVideoHorizontalBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(lesson: ListItem.VideoItem) {
        binding.tvTitle.text = lesson.name
        YouTubeThumbnail.loadThumbnail(binding.ivThumbnail, lesson.url)
    }

}