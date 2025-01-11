package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkLessonDto
import com.theberdakh.fitness.domain.model.Lesson
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel


fun NetworkLesson.toDomain() = Lesson(id = id, name = title, url = youtubeUrl, module = moduleId, isFree = isFree)

fun NetworkLessonDto.toDomain() = Lesson(id = id, name = title, url = "", module = moduleId, isFree = isFree ?: false)

fun Lesson.toVideoItem() = ListItem.VideoItem(id = id, name = name, url = url, module = module.toString())
fun List<Lesson>.toVideoItems() = this.map { it.toVideoItem() }

fun Lesson.toFreeLessonItem() = FreeLessonItem.FreeLessonVideoItem(id = id, name = name, url = url)
fun List<Lesson>.toFreeLessonItems() = this.map { it.toFreeLessonItem() }


fun Lesson.toLessonsModelLesson(isAvailable: Boolean) = LessonsModel.Lesson(id = id, title = name, youtubeUrl = url, isFree = isFree, isAvailable = isAvailable)
fun List<Lesson>.toLessonsModelLesson(isAvailable: Boolean) = this.map { it.toLessonsModelLesson(isAvailable) }