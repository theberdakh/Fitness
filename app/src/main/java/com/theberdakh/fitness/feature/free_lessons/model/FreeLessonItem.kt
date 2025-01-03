package com.theberdakh.fitness.feature.free_lessons.model

sealed class FreeLessonItem {
    data class FreeLessonVideoItem(val id: Int, val name: String, val url: String): FreeLessonItem()
    data object LoadingItem: FreeLessonItem()
}