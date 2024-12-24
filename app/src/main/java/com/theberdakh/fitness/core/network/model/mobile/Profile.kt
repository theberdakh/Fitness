package com.theberdakh.fitness.core.network.model.mobile

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName

data class Profile(
    val id: Int,
    val name: String,
    val phone: String,
    @SerializedName("target_id")
    val targetId: Int,
    val target: Target
)
