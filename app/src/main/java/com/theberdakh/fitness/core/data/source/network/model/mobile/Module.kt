package com.theberdakh.fitness.core.data.source.network.model.mobile

import com.google.gson.annotations.SerializedName

data class Module(
    val id: Int,
    val title: String,
    @SerializedName("pack_id")
    val packId: Int
)

data class OrderModule(
    val id: Int,
    val title: String,
    @SerializedName("pack_id")
    val packId: Int,
    @SerializedName("is_view_finished")
    val isViewFinished: Boolean,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    val lessons: List<OrderLesson>
)