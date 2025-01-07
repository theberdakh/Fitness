package com.theberdakh.fitness.feature.lessons.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class LessonsModelAdapter: ListAdapter<LessonsModel, LessonsModelViewHolder>(LessonsModelDiffUtilCallback) {

    private var onLessonClick: ((LessonsModel.Lesson) -> Unit)? = null
    fun setOnLessonClick(onLessonClick: ((LessonsModel.Lesson) -> Unit)?){
        this.onLessonClick = onLessonClick
    }

    private var onLessonUnavailable: ((LessonsModel.Lesson) -> Unit)? = null
    fun setOnLessonUnavailable(onLessonUnavailable: ((LessonsModel.Lesson) -> Unit)?){
        this.onLessonUnavailable = onLessonUnavailable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsModelViewHolder {
        return LessonsModelViewHolder.create(viewType, parent, onLessonClick, onLessonUnavailable)
    }

    override fun onBindViewHolder(holder: LessonsModelViewHolder, position: Int) {
        when(getItem(position)){
            is LessonsModel.Lesson -> (holder as LessonsModelViewHolder.LessonViewHolder).bind(getItem(position) as LessonsModel.Lesson)
        }
    }

}