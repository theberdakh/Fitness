package com.theberdakh.fitness.core.data.source.network.retrofit


import android.util.Log
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.data.source.NetworkServerError
import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkMessage
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface FitnessNetworkApi {

    @Headers("Accept: application/json")
    @POST(value = "api/v1/auth/send-code")
    suspend fun sendCode(@Body body: NetworkSendCodeRequest): Response<NetworkMessage>

    @POST(value = "api/v1/auth/login")
    suspend fun login(@Body body: NetworkLoginRequest): Response<NetworkLoginResponse>

    @DELETE(value = "api/v1/auth/logout")
    suspend fun logout(): Response<String>

    @GET("api/v1/mobile/targets")
    suspend fun getTargets(): Response<List<NetworkTarget>>

    @GET("api/v1/mobile/profile")
    suspend fun getProfile(): Response<NetworkProfile>

    @PATCH("api/v1/mobile/profile")
    suspend fun updateName(@Body body: NetworkUpdateNameRequest): Response<NetworkProfile>

    @GET("api/v1/mobile/packs")
    suspend fun getSubscriptionPacks(): Response<List<NetworkPack>>

    @GET("api/v1/mobile/modules")
    suspend fun getModules(@Query("pack_id") packId: Int): Response<List<NetworkModule>>

    @GET("api/v1/mobile/lessons")
    suspend fun getLessons(@Query("module_id") moduleId: Int): Response<List<NetworkLesson>>

    @GET("api/v1/mobile/random-free-lessons")
    suspend fun getRandomFreeLessons(): Response<List<NetworkLesson>>

    @GET("api/v1/mobile/free-lessons")
    suspend fun getFreeLessons(
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: String?
    ): Response<List<NetworkLesson>>

    @GET("api/v1/mobile/lessons/{lessonId}")
    suspend fun getLesson(@Path("lessonId") lessonId: Int): Response<NetworkLesson>

    @GET("api/v1/mobile/my/orders")
    suspend fun getMyOrders(): Response<List<NetworkOrder>>

    @GET("api/v1/mobile/my/modules")
    suspend fun getModulesByOrderId(@Query("order_id") orderId: Int): Response<List<NetworkOrderModule>>
}

/**
 * Simple wrapper to make request and wrap to NetworkResult
 * */
suspend fun <T> makeRequest(apiCall: suspend () -> Response<T>): NetworkResult<T> = try {
    val response = apiCall()
    if (response.isSuccessful && response.body() != null) {
        Log.i("RetrofitFitnessNetwork", "msg: ${response.message()}")
        response.body()?.let {
            NetworkResult.Success(it)
        } ?: NetworkResult.Error("Empty response body")
    } else {
        val errorBody = response.errorBody()?.string()
        errorBody?.let {
            val networkServerError = Json.decodeFromString<NetworkServerError>(it)
            NetworkResult.Error(networkServerError.message)
        } ?: NetworkResult.Error(message = "Error ${response.code()}: ${response.message()}")
    }
} catch (e: Exception) {
    NetworkResult.Error(message = "Error ${e.message}", exception = e)
}

/**
 * Retrofit API declaration for FitnessNetwork API
 * */
class RetrofitFitnessNetwork(private val api: FitnessNetworkApi) : FitnessNetworkDataSource {

    override suspend fun sendCode(request: NetworkSendCodeRequest) =
        makeRequest { api.sendCode(request) }

    override suspend fun login(request: NetworkLoginRequest) = makeRequest { api.login(request) }

    override suspend fun logout() = makeRequest { api.logout() }

    override suspend fun getTargets() = makeRequest { api.getTargets() }

    override suspend fun getProfile() = makeRequest { api.getProfile() }

    override suspend fun updateName(request: NetworkUpdateNameRequest) =
        makeRequest { api.updateName(request) }

    override suspend fun getSubscriptionPacks() = makeRequest { api.getSubscriptionPacks() }

    override suspend fun getModules(packId: Int) = makeRequest { api.getModules(packId) }

    override suspend fun getLessons(moduleId: Int) = makeRequest { api.getLessons(moduleId) }

    override suspend fun getRandomFreeLessons() = makeRequest { api.getRandomFreeLessons() }

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ) = makeRequest { api.getFreeLessons(perPage, cursor) }

    override suspend fun getLesson(lessonId: Int) = makeRequest { api.getLesson(lessonId) }

    override suspend fun getMyOrders() = makeRequest { api.getMyOrders() }

    override suspend fun getModulesByOrderId(orderId: Int) =
        makeRequest { api.getModulesByOrderId(orderId) }
}
