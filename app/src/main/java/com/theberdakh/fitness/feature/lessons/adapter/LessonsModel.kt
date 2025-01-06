package com.theberdakh.fitness.feature.lessons.adapter

import androidx.recyclerview.widget.DiffUtil

sealed class LessonsModel {
    data class Lesson(val id: Int, val title: String, val youtubeUrl: String, val isFree: Boolean): LessonsModel()

    companion object {
        const val VIEW_TYPE_LESSON = 0
    }
}

object LessonsModelDiffUtilCallback: DiffUtil.ItemCallback<LessonsModel>(){
    override fun areItemsTheSame(oldItem: LessonsModel, newItem: LessonsModel): Boolean {
        return when(oldItem){
            is LessonsModel.Lesson -> oldItem.id == (newItem as LessonsModel.Lesson).id
        }
    }

    override fun areContentsTheSame(oldItem: LessonsModel, newItem: LessonsModel): Boolean {
        return oldItem == newItem
    }

}
