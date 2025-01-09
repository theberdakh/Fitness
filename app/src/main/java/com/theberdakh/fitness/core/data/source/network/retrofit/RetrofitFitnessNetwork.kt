package com.theberdakh.fitness.core.data.source.network.retrofit


import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkMessage
import com.theberdakh.fitness.core.data.source.network.model.PagingResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface FitnessNetworkApi {

    @POST(value = "api/v1/auth/send-code")
    suspend fun sendCode(@Body body: NetworkSendCodeRequest): NetworkMessage

    @POST(value = "api/v1/auth/login")
    suspend fun login(@Body body: NetworkLoginRequest): NetworkResponse<NetworkLoginResponse>

    @DELETE(value = "api/v1/auth/logout")
    suspend fun logout(): NetworkResponse<String>

    @GET("api/v1/mobile/targets")
    suspend fun getTargets(): NetworkResponse<List<NetworkTarget>>

    @GET("api/v1/mobile/profile")
    suspend fun getProfile(): NetworkResponse<NetworkProfile>

    @PATCH("api/v1/mobile/profile")
    suspend fun updateName(@Body body: NetworkUpdateNameRequest): NetworkResponse<NetworkProfile>

    @GET("api/v1/mobile/packs")
    suspend fun getSubscriptionPacks(): NetworkResponse<List<NetworkPack>>

    @GET("api/v1/mobile/modules")
    suspend fun getModules(@Query("pack_id") packId: Int): NetworkResponse<List<NetworkModule>>

    @GET("api/v1/mobile/lessons")
    suspend fun getLessons(@Query("module_id") moduleId: Int): NetworkResponse<List<NetworkLesson>>

    @GET("api/v1/mobile/random-free-lessons")
    suspend fun getRandomFreeLessons(): NetworkResponse<List<NetworkLesson>>

    @GET("api/v1/mobile/free-lessons")
    suspend fun getFreeLessons(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: String?
    ): PagingResponse<NetworkLesson>

    @GET("api/v1/mobile/lessons/{lessonId}")
    suspend fun getLesson(@Path("lessonId") lessonId: Int): NetworkResponse<NetworkLesson>

    @GET("api/v1/mobile/my/orders")
    suspend fun getMyOrders(): NetworkResponse<List<NetworkOrder>>

    @GET("api/v1/mobile/my/modules")
    suspend fun getModulesByOrderId(@Query("order_id") orderId: Int): NetworkResponse<List<NetworkOrderModule>>
}

/**
 * Wrapper for data provided from the base url]*/
data class NetworkResponse<T>(
    val message: String,
    val data: T
)

/**
 * Retrofit API declaration for FitnessNetwork API */

class RetrofitFitnessNetwork(private val api: FitnessNetworkApi) : FitnessNetworkDataSource {

    override suspend fun sendCode(request: NetworkSendCodeRequest): NetworkMessage {
        return api.sendCode(request)
    }

    override suspend fun login(request: NetworkLoginRequest): NetworkLoginResponse {
        return api.login(request).data
    }

    override suspend fun logout(): String {
        return api.logout().data
    }

    override suspend fun getTargets(): List<NetworkTarget> {
        return api.getTargets().data
    }

    override suspend fun getProfile(): NetworkProfile {
        return api.getProfile().data
    }

    override suspend fun updateName(request: NetworkUpdateNameRequest): NetworkProfile {
        return api.updateName(request).data
    }

    override suspend fun getSubscriptionPacks(): List<NetworkPack> {
        return api.getSubscriptionPacks().data
    }

    override suspend fun getModules(packId: Int): List<NetworkModule> {
        return api.getModules(packId).data
    }

    override suspend fun getLessons(moduleId: Int): List<NetworkLesson> {
        return api.getLessons(moduleId).data
    }

    override suspend fun getRandomFreeLessons(): List<NetworkLesson> {
        return api.getRandomFreeLessons().data
    }

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ): List<NetworkLesson> {
        return api.getFreeLessons(perPage, cursor).data
    }

    override suspend fun getLesson(lessonId: Int): NetworkLesson {
        return api.getLesson(lessonId).data
    }

    override suspend fun getMyOrders(): List<NetworkOrder> {
        return api.getMyOrders().data
    }

    override suspend fun getModulesByOrderId(orderId: Int): List<NetworkOrderModule> {
        return api.getModulesByOrderId(orderId).data
    }
}
