package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkModule(
    val id: Int,
    val title: String,
    @SerializedName("pack_id")
    val packId: Int
)

data class NetworkOrderModule(
    val id: Int,
    val title: String,
    @SerializedName("pack_id")
    val packId: Int,
    @SerializedName("is_view_finished")
    val isViewFinished: Boolean,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    val lessons: List<NetworkLessonDto>
)

data class NetworkLessonDto(
    val id: Int,
    val title: String,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("is_free")
    val isFree: Boolean?,
)