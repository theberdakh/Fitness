package com.theberdakh.fitness.core.network.model.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    val phone: String,
    @SerializedName("verification_code")
    val verificationCode: String
)

data class LoginResponse(
    val user: LoginResponseUser?,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val tokenType: String?,

)

data class LoginResponseUser(
    val id: Int?,
    val name: String?,
    val phone: String?,
    @SerializedName("target_id")
    val targetId: Int?
)
