package com.theberdakh.fitness.data.network.model

import com.google.gson.annotations.SerializedName

data class ServerResponse<T>(
    val message: String,
    val data: T,
    val links: PagingLinks?,
    val meta: PagingMeta?
)

data class ServerMessage(
    val message: String
)

data class PagingMeta(
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("next_cursor")
    val nextCursor: String?,
    @SerializedName("prev_cursor")
    val prevCursor: String?
)

data class PagingLinks(
    val first: String?,
    val last: String?,
    val prev: String?,
    val next: String?
)
