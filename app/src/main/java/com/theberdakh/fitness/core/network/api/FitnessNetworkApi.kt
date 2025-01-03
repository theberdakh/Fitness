package com.theberdakh.fitness.core.network.api

import android.database.Cursor
import com.theberdakh.fitness.core.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.PagingResponse
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.LoginResponse
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import com.theberdakh.fitness.core.network.model.mobile.Lesson
import com.theberdakh.fitness.core.network.model.mobile.Module
import com.theberdakh.fitness.core.network.model.mobile.Pack
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.core.network.model.mobile.Target
import com.theberdakh.fitness.core.network.model.mobile.UpdateNameRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface FitnessNetworkApi {

    @POST(value = "api/v1/auth/send-code")
    suspend fun sendCode(@Body body: SendCodeRequestBody): Response<MessageModel>

    @POST(value = "api/v1/auth/login")
    suspend fun login(@Body body: LoginRequestBody): Response<BaseNetworkModel<LoginResponse>>

    @DELETE(value = "api/v1/auth/logout")
    suspend fun logout(): Response<BaseNetworkModel<String>>

    @GET("api/v1/mobile/targets")
    suspend fun getTargets(): Response<BaseNetworkModel<List<Target>>>

    @GET("api/v1/mobile/profile")
    suspend fun getProfile(): Response<BaseNetworkModel<Profile>>

    @PATCH("api/v1/mobile/profile")
    suspend fun updateName(@Body body: UpdateNameRequestBody): Response<BaseNetworkModel<Profile>>

    @GET("api/v1/mobile/packs")
    suspend fun getSubscriptionPacks(): Response<BaseNetworkModel<List<Pack>>>

    @GET("api/v1/mobile/modules")
    suspend fun getModules(@Query("pack_id") packId: Int): Response<BaseNetworkModel<List<Module>>>

    @GET("api/v1/mobile/lessons")
    suspend fun getLessons(@Query("module_id") moduleId: Int): Response<BaseNetworkModel<List<Lesson>>>

    @GET("api/v1/mobile/random-free-lessons")
    suspend fun getRandomFreeLessons(): Response<BaseNetworkModel<List<Lesson>>>

    @GET("api/v1/mobile/free-lessons")
    suspend fun getFreeLessons(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: String?
    ): Response<PagingResponse<Lesson>>

}
