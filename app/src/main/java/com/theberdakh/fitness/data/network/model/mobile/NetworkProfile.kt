package com.theberdakh.fitness.data.network.model.mobile

import com.google.gson.annotations.SerializedName

data class NetworkProfile(
    val id: Int? = null,
    val name: String = "",
    val phone: String = "",
    @SerializedName("target_id")
    val targetId: Int? = null,
    val target: NetworkTarget? = null
)
data class NetworkUpdateNameRequest(val name: String)



