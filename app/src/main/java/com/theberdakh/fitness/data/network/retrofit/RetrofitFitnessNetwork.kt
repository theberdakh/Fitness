package com.theberdakh.fitness.data.network.retrofit


import android.util.Log
import com.google.gson.annotations.SerializedName
import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.NetworkServerMessage
import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.NetworkServerError
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
    suspend fun logout(): Response<String>

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
    suspend fun postMessage(@Body body: NetworkMessageRequest): Response<NetworkMessage>
}

/**
 * Paging model */
class PaginationList<T>(
    val message: String,
    val data: List<T>,
    val links: PagingLinks,
    val meta: PagingMeta
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

/** Some responses contain message with data*/
data class ServerResponse<T>(
    val message: String,
    val data: T
)

/** With this extension function, NetworkDataSource no longer dependent on ServerResponse.
 * This also prevents confusing calls like data.data. on ViewModel layer
 * */
fun <T> NetworkResult<ServerResponse<T>>.unwrap(): NetworkResult<T> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(data = this.data.data)
        is NetworkResult.Error -> NetworkResult.Error(
            message = this.message,
            exception = this.exception
        )
    }
}

/**
 * Simple wrapper to make request and wrap to NetworkResult
 * */
suspend fun <T> makeRequest(apiCall: suspend () -> Response<T>): NetworkResult<T> = try {
    val response = apiCall()
    Log.i("RetrofitFitnessNetwork", "raw: ${response.body().toString()}")
    Log.i("RetrofitFitnessNetwork", "headers: ${response.headers()}")
    if (response.isSuccessful && response.body() != null) {
        response.body()?.let {
            val responseBody = it
            NetworkResult.Success(data = responseBody)
        } ?: NetworkResult.Error("Empty response body")
    } else {
        val errorBody = response.errorBody()?.string()
        when (response.code()) {
            422 -> {
                errorBody?.let {
                    val networkServerError = Json.decodeFromString<NetworkServerError>(it)
                    NetworkResult.Error(networkServerError.message)
                }
                    ?: NetworkResult.Error(message = "Error ${response.code()}: ${response.message()}")
            }

            401 -> NetworkResult.Error(message = "Unauthorized")
            else -> {
                errorBody?.let {
                    val networkServerError = Json.decodeFromString<NetworkServerMessage>(it)
                    NetworkResult.Error(networkServerError.message)
                }
                    ?: NetworkResult.Error(message = "Error ${response.code()}: ${response.message()}")
            }
        }
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

    override suspend fun login(request: NetworkLoginRequest): NetworkResult<NetworkLoginResponse> {
        return makeRequest { api.login(request) }.unwrap()
    }

    override suspend fun logout() = makeRequest { api.logout() }

    override suspend fun getTargets() = makeRequest { api.getTargets() }.unwrap()

    override suspend fun getProfile() = makeRequest { api.getProfile() }.unwrap()

    override suspend fun updateName(request: NetworkUpdateNameRequest) =
        makeRequest { api.updateName(request) }

    override suspend fun getSubscriptionPacks() =
        makeRequest { api.getSubscriptionPacks() }.unwrap()

    override suspend fun getModules(packId: Int) = makeRequest { api.getModules(packId) }.unwrap()

    override suspend fun getLessons(moduleId: Int) =
        makeRequest { api.getLessons(moduleId) }.unwrap()

    override suspend fun getRandomFreeLessons() =
        makeRequest { api.getRandomFreeLessons() }.unwrap()

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ): NetworkResult<List<NetworkLesson>> {
        return makeRequest { api.getFreeLessons(perPage, cursor) }.unwrap()
    }

    override suspend fun getLesson(lessonId: Int) = makeRequest { api.getLesson(lessonId) }.unwrap()

    override suspend fun getMyOrders() = makeRequest { api.getMyOrders() }.unwrap()

    override suspend fun getModulesByOrderId(orderId: Int) =
        makeRequest { api.getModulesByOrderId(orderId) }.unwrap()

    override suspend fun getNotifications() = makeRequest { api.getNotifications() }.unwrap()
    override suspend fun getNotification(notificationId: Int): NetworkResult<NetworkNotificationDetail> =
        makeRequest { api.getNotification(notificationId) }.unwrap()

    override suspend fun getMessages(): NetworkResult<List<NetworkMessage>> {
        return makeRequest { api.getMessages() }.unwrap()
    }

    override suspend fun sendMessage(message: String): NetworkResult<NetworkMessage> {
        return makeRequest { api.postMessage(NetworkMessageRequest(message)) }
    }

    override suspend fun getAllOrders(): NetworkResult<List<NetworkOrder>> {
        return makeRequest { api.getAllOrders() }.unwrap()
    }

}
