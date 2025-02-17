package com.theberdakh.fitness.data.network.retrofit

import com.theberdakh.fitness.data.network.NetworkServerMessage
import com.theberdakh.fitness.data.network.model.ServerMessage
import com.theberdakh.fitness.data.network.model.ServerResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkMessage
import com.theberdakh.fitness.data.network.model.mobile.NetworkMessageRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkNotification
import com.theberdakh.fitness.data.network.model.mobile.NetworkNotificationDetail
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.data.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit API declaration for FitnessNetwork API
 * Don't remove Accept: application/json header, it's required to get right error code and error body as without it, Retrofit will return 200 status even it's error
 * */

interface FitnessNetworkApi {

    @Headers("Accept: application/json")
    @POST(value = "api/v1/auth/send-code")
    suspend fun sendCode(@Body body: NetworkSendCodeRequest): Response<NetworkServerMessage>

    @Headers("Accept: application/json")
    @POST(value = "api/v1/auth/login")
    suspend fun login(@Body body: NetworkLoginRequest): Response<ServerResponse<NetworkLoginResponse>>

    @Headers("Accept: application/json")
    @DELETE(value = "api/v1/auth/logout")
    suspend fun logout(): Response<ServerMessage>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/targets")
    suspend fun getTargets(): Response<ServerResponse<List<NetworkTarget>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/profile")
    suspend fun getProfile(): Response<ServerResponse<NetworkProfile>>

    @Headers("Accept: application/json")
    @PATCH("api/v1/mobile/profile")
    suspend fun updateName(@Body body: NetworkUpdateNameRequest): Response<NetworkProfile>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/packs")
    suspend fun getSubscriptionPacks(): Response<ServerResponse<List<NetworkPack>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/modules")
    suspend fun getModules(@Query("pack_id") packId: Int): Response<ServerResponse<List<NetworkModule>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/lessons")
    suspend fun getLessons(@Query("module_id") moduleId: Int): Response<ServerResponse<List<NetworkLesson>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/random-free-lessons")
    suspend fun getRandomFreeLessons(): Response<ServerResponse<List<NetworkLesson>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/free-lessons")
    suspend fun getFreeLessons(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: String?
    ): Response<ServerResponse<List<NetworkLesson>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/lessons/{lessonId}")
    suspend fun getLesson(@Path("lessonId") lessonId: Int): Response<ServerResponse<NetworkLesson>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/orders")
    suspend fun getAllOrders(): Response<ServerResponse<List<NetworkOrder>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/my/orders")
    suspend fun getMyOrders(): Response<ServerResponse<List<NetworkOrder>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/my/modules")
    suspend fun getModulesByOrderId(@Query("order_id") orderId: Int): Response<ServerResponse<List<NetworkOrderModule>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/notifications")
    suspend fun getNotifications(): Response<ServerResponse<List<NetworkNotification>>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/notifications/{notificationId}")
    suspend fun getNotification(@Path("notificationId") notificationId: Int): Response<ServerResponse<NetworkNotificationDetail>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/messages")
    suspend fun getMessages(): Response<ServerResponse<List<NetworkMessage>>>

    @Headers("Accept: application/json")
    @POST("api/v1/mobile/messages")
    suspend fun postMessage(@Body body: NetworkMessageRequest): Response<ServerResponse<NetworkMessage>>

    @Headers("Accept: application/json")
    @GET("api/v1/mobile/free-lessons")
    fun getFreeLessonsPaging(perPage: Int, cursor: String?): Response<ServerResponse<List<NetworkLesson>>>
}