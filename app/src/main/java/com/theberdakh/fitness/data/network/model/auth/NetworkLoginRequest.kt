package com.theberdakh.fitness.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class NetworkLoginRequest(
    val phone: String,
    @SerializedName("verification_code")
    val verificationCode: String
)

data class NetworkLoginResponse(
    val user: NetworkLoginUserDto,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String
)

data class NetworkLoginUserDto(
    val id: Int,
    val name: String?,
    val phone: String,
    @SerializedName("target_id")
    val targetId: Int? = null,
) {
    companion object {
        const val NO_TARGET_ID = -1
    }
}
