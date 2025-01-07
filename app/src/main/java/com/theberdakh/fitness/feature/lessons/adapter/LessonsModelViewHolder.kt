package com.theberdakh.fitness.feature.lessons.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.fitness.R
import com.theberdakh.fitness.databinding.ItemVideoHorizontalBinding
import com.theberdakh.fitness.feature.home.utils.YouTubeThumbnail

sealed class LessonsModelViewHolder private constructor(view: View) :
    RecyclerView.ViewHolder(view) {

    class LessonViewHolder private constructor(
        private val binding: ItemVideoHorizontalBinding,
        private val onLessonClick: ((LessonsModel.Lesson) -> Unit)?,
        private val onLessonUnavailable: ((LessonsModel.Lesson) -> Unit)? = null
    ) : LessonsModelViewHolder(binding.root) {
        fun bind(lesson: LessonsModel.Lesson) {
            binding.tvTitle.text = lesson.title
            YouTubeThumbnail.loadThumbnail(binding.ivThumbnail, lesson.youtubeUrl)
            if (!lesson.isAvailable) {
                binding.tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_lock_24,
                    0
                )
            }

            binding.root.setOnClickListener {
                if (lesson.isAvailable) {
                    onLessonClick?.invoke(lesson)
                } else {
                   onLessonUnavailable?.invoke(lesson)
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onLessonClick: ((LessonsModel.Lesson) -> Unit)?,
                onLessonUnavailable: ((LessonsModel.Lesson) -> Unit)?
            ): LessonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemVideoHorizontalBinding.inflate(layoutInflater, parent, false)
                return LessonViewHolder(binding, onLessonClick, onLessonUnavailable)
            }
        }
    }

    companion object {
        fun create(
            viewType: Int,
            viewGroup: ViewGroup,
            onLessonClick: ((LessonsModel.Lesson) -> Unit)?,
            onLessonUnavailable: ((LessonsModel.Lesson) -> Unit)?
        ): LessonsModelViewHolder {
            return when (viewType) {
                LessonsModel.VIEW_TYPE_LESSON -> LessonViewHolder.from(viewGroup, onLessonClick, onLessonUnavailable)
                else -> throw IllegalArgumentException("Unknown view type")
            }
        }
    }
}