package com.theberdakh.fitness.core.data.network.api

import com.theberdakh.fitness.core.data.network.model.BaseNetworkModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST

interface FitnessNetworkApi {

    @POST(value = "/api/v1/auth/send-code")
    fun sendCode(): Response<BaseNetworkModel<String>>

    @POST(value = "/api/v1/auth/login")
    fun login(): Response<BaseNetworkModel<String>>

    @DELETE(value = "/api/v1/auth/logout")
    fun logout(): Response<BaseNetworkModel<String>>
}
