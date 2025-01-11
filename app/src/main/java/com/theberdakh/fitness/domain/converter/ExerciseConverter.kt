package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkLessonCheckListDto
import com.theberdakh.fitness.domain.model.Exercise
import com.theberdakh.fitness.feature.lesson.checklist.model.ChecklistItem

fun NetworkLessonCheckListDto.toDomain() = Exercise(name = this.title)

fun Exercise.toCheckListItem() = ChecklistItem(id = 0, title = name, isChecked = false)
fun List<Exercise>.toCheckListItems() = this.map { it.toCheckListItem() }
