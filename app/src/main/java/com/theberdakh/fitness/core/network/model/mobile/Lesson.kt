package com.theberdakh.fitness.core.network.model.mobile

import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.lesson.checklist.model.ChecklistItem

data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("youtube_url")
    val youtubeUrl: String,
    @SerializedName("is_free")
    val isFree: Boolean?,
    val checklists: List<LessonCheckList>
)

data class OrderLesson(
    val id: Int,
    val title: String,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("youtube_url")
    val youtubeUrl: String,
    @SerializedName("is_free")
    val isFree: Boolean?
)

data class LessonCheckList(
    val title: String
)

fun LessonCheckList.toCheckListItem(): ChecklistItem = ChecklistItem(id = 0, title = title, isChecked = false)

fun Lesson.toVideoItem() = ListItem.VideoItem(id = id, title, youtubeUrl, module = moduleId.toString())
fun Lesson.toFreeLessonItem() = FreeLessonItem.FreeLessonVideoItem(id = id, name = this.title, url = this.youtubeUrl)