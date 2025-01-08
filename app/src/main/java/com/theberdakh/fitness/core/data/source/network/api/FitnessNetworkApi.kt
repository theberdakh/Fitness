package com.theberdakh.fitness.core.data.source.network.api


import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.handler.NetworkResponseHandler
import com.theberdakh.fitness.core.data.source.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.data.source.network.model.MessageModel
import com.theberdakh.fitness.core.data.source.network.model.PagingResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.core.data.source.network.model.mobile.Module
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FitnessNetworkApi {

    @POST(value = "api/v1/auth/send-code")
    suspend fun sendCode(@Body body: NetworkSendCodeRequest): Response<MessageModel>

    @POST(value = "api/v1/auth/login")
    suspend fun login(@Body body: NetworkLoginRequest): Response<BaseNetworkModel<NetworkLoginResponse>>

    @DELETE(value = "api/v1/auth/logout")
    suspend fun logout(): Response<BaseNetworkModel<String>>

    @GET("api/v1/mobile/targets")
    suspend fun getTargets(): Response<BaseNetworkModel<List<NetworkTarget>>>

    @GET("api/v1/mobile/profile")
    suspend fun getProfile(): Response<BaseNetworkModel<NetworkProfile>>

    @PATCH("api/v1/mobile/profile")
    suspend fun updateName(@Body body: NetworkUpdateNameRequest): Response<BaseNetworkModel<NetworkProfile>>

    @GET("api/v1/mobile/packs")
    suspend fun getSubscriptionPacks(): Response<BaseNetworkModel<List<NetworkPack>>>

    @GET("api/v1/mobile/modules")
    suspend fun getModules(@Query("pack_id") packId: Int): Response<BaseNetworkModel<List<Module>>>

    @GET("api/v1/mobile/lessons")
    suspend fun getLessons(@Query("module_id") moduleId: Int): Response<BaseNetworkModel<List<NetworkLesson>>>

    @GET("api/v1/mobile/random-free-lessons")
    suspend fun getRandomFreeLessons(): Response<BaseNetworkModel<List<NetworkLesson>>>

    @GET("api/v1/mobile/free-lessons")
    suspend fun getFreeLessons(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: String?
    ): Response<PagingResponse<NetworkLesson>>

    @GET("api/v1/mobile/lessons/{lessonId}")
    suspend fun getLesson(@Path("lessonId") lessonId: Int): Response<BaseNetworkModel<NetworkLesson>>

    @GET("api/v1/mobile/my/orders")
    suspend fun getMyOrders(): Response<BaseNetworkModel<List<NetworkOrder>>>
}

/*
internal class RetrofitFitnessNetwork(private val api: FitnessNetworkApi): FitnessNetworkDataSource, NetworkResponseHandler() {

    override suspend fun sendCode(request: NetworkSendCodeRequest): MessageModel  {

    }

    override suspend fun login(request: NetworkLoginRequest): NetworkLoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getTargets(): List<Target> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): NetworkProfile {
        TODO("Not yet implemented")
    }

    override suspend fun updateName(request: NetworkUpdateNameRequest): NetworkProfile {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscriptionPacks(): List<NetworkPack> {
        TODO("Not yet implemented")
    }

    override suspend fun getModules(packId: Int): List<Module> {
        TODO("Not yet implemented")
    }

    override suspend fun getLessons(moduleId: Int): List<NetworkLesson> {
        TODO("Not yet implemented")
    }

    override suspend fun getRandomFreeLessons(): List<NetworkLesson> {
        TODO("Not yet implemented")
    }

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ): PagingResponse<NetworkLesson> {
        TODO("Not yet implemented")
    }

    override suspend fun getLesson(lessonId: Int): NetworkLesson {
        TODO("Not yet implemented")
    }

    override suspend fun getMyOrders(): List<NetworkOrder> {
        TODO("Not yet implemented")
    }


}
*/
