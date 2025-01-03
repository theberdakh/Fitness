package com.theberdakh.fitness.feature.lesson.checklist.model

data class ChecklistItem(
    val id: Int,
    val title: String,
    var isChecked: Boolean
)