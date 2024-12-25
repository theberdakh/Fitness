package com.theberdakh.fitness.core.network.model.mobile

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName

data class Profile(
    val id: Int? = null,
    val name: String = "",
    val phone: String = "",
    @SerializedName("target_id")
    val targetId: Int? = null,
    val target: Target? = null
)

data class UpdateNameRequestBody(
    val name: String
)


