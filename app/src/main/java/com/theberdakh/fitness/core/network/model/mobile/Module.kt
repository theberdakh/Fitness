package com.theberdakh.fitness.core.network.model.mobile

import com.google.gson.annotations.SerializedName

data class Module(
    val id: Int,
    val title: String,
    @SerializedName("pack_id")
    val packId: Int
)