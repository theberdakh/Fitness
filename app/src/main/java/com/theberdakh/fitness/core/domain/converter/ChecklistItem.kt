package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLessonCheckListDto
import com.theberdakh.fitness.feature.lesson.checklist.model.ChecklistItem

fun NetworkLessonCheckListDto.toCheckListItem(): ChecklistItem = ChecklistItem(id = 0, title = title, isChecked = false)