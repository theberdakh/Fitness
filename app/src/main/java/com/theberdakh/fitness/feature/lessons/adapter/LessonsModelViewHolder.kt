package com.theberdakh.fitness.feature.lessons.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.databinding.ItemVideoHorizontalBinding
import com.theberdakh.fitness.feature.home.utils.YouTubeThumbnail

sealed class LessonsModelViewHolder private constructor(view: View): RecyclerView.ViewHolder(view){

    class LessonViewHolder private constructor(private val binding: ItemVideoHorizontalBinding, private val onLessonClick: ((LessonsModel.Lesson) -> Unit)?): LessonsModelViewHolder(binding.root){
        fun bind(lesson: LessonsModel.Lesson){
            binding.tvTitle.text = lesson.title
            YouTubeThumbnail.loadThumbnail(binding.ivThumbnail, lesson.youtubeUrl)
            binding.root.setOnClickListener {
                onLessonClick?.invoke(lesson)
            }
        }

        companion object {
            fun from(parent: ViewGroup, onLessonClick: ((LessonsModel.Lesson) -> Unit)?): LessonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemVideoHorizontalBinding.inflate(layoutInflater, parent, false)
                return LessonViewHolder(binding, onLessonClick)
            }
        }
    }

    companion object {
        fun create(viewType: Int, viewGroup: ViewGroup, onLessonClick: ((LessonsModel.Lesson) -> Unit)?): LessonsModelViewHolder {
            return when(viewType){
                LessonsModel.VIEW_TYPE_LESSON -> LessonViewHolder.from(viewGroup, onLessonClick)
                else -> throw IllegalArgumentException("Unknown view type")
            }
        }
    }
}