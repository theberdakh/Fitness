package com.theberdakh.fitness.core.network.api

import com.theberdakh.fitness.core.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.LoginResponse
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.core.network.model.mobile.Target
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface FitnessNetworkApi {

    @POST(value = "auth/send-code")
    suspend fun sendCode(@Body body: SendCodeRequestBody): Response<MessageModel>

    @POST(value = "auth/login")
    suspend fun login(@Body body: LoginRequestBody): Response<BaseNetworkModel<LoginResponse>>

    @DELETE(value = "auth/logout")
    suspend fun logout(): Response<BaseNetworkModel<String>>

    @GET("mobile/targets")
    suspend fun getTargets(): Response<BaseNetworkModel<List<Target>>>

    @GET("mobile/profile")
    suspend fun getProfile(): Response<BaseNetworkModel<Profile>>

}
