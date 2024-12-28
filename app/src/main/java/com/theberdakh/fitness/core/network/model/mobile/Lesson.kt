package com.theberdakh.fitness.core.network.model.mobile

import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.feature.home.model.ListItem

data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("youtube_url")
    val youtubeUrl: String,
    @SerializedName("is_free")
    val isFree: Boolean
)

fun Lesson.toVideoItem() = ListItem.VideoItem(title, youtubeUrl, module = moduleId.toString())
