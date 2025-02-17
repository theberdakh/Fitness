package com.theberdakh.fitness.data.network

import androidx.paging.PagingData
import com.theberdakh.fitness.data.network.model.ServerMessage
import com.theberdakh.fitness.data.network.model.ServerResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkMessage
import com.theberdakh.fitness.data.network.model.mobile.NetworkModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkNotification
import com.theberdakh.fitness.data.network.model.mobile.NetworkNotificationDetail
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.data.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import kotlinx.coroutines.flow.Flow

/*Inspired from https://github.com/android/nowinandroid/blob/main/core/network/src/main/kotlin/com/google/samples/apps/nowinandroid/core/network/NiaNetworkDataSource.kt */
interface FitnessNetworkDataSource {

    suspend fun sendCode(request: NetworkSendCodeRequest): NetworkResult<NetworkServerMessage>

    suspend fun login(request: NetworkLoginRequest): NetworkResult<NetworkLoginResponse>

    suspend fun logout(): NetworkResult<ServerMessage>

    suspend fun getTargets(): NetworkResult<List<NetworkTarget>>

    suspend fun getProfile(): NetworkResult<NetworkProfile>

    suspend fun updateName(request: NetworkUpdateNameRequest): NetworkResult<NetworkProfile>

    suspend fun getSubscriptionPacks(): NetworkResult<List<NetworkPack>>

    suspend fun getModules(packId: Int): NetworkResult<List<NetworkModule>>

    suspend fun getLessons(moduleId: Int): NetworkResult<List<NetworkLesson>>

    suspend fun getRandomFreeLessons(): NetworkResult<List<NetworkLesson>>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): NetworkResult<List<NetworkLesson>>

    suspend fun getFreeLessonsPaging(perPage: Int, cursor: String?): Flow<PagingData<NetworkLesson>>

    suspend fun getLesson(lessonId: Int): NetworkResult<NetworkLesson>

    suspend fun getMyOrders(): NetworkResult<List<NetworkOrder>>

    suspend fun getModulesByOrderId(orderId: Int): NetworkResult<List<NetworkOrderModule>>

    suspend fun getNotifications(): NetworkResult<List<NetworkNotification>>

    suspend fun getNotification(notificationId: Int): NetworkResult<NetworkNotificationDetail>

    suspend fun getMessages(): NetworkResult<List<NetworkMessage>>

    suspend fun sendMessage(message: String): NetworkResult<NetworkMessage>

    suspend fun getAllOrders(): NetworkResult<List<NetworkOrder>>
}