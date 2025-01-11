package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import com.theberdakh.fitness.feature.home.model.ListItem
import com.theberdakh.fitness.feature.lesson.checklist.model.ChecklistItem

data class NetworkLesson(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("youtube_url")
    val youtubeUrl: String,
    @SerializedName("is_free")
    val isFree: Boolean,
    val checklists: List<NetworkLessonCheckListDto>
)

data class NetworkLessonCheckListDto(
    val title: String
)


