package com.theberdakh.fitness.core.data.source.network

import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkMessage
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest

/*Inspired from https://github.com/android/nowinandroid/blob/main/core/network/src/main/kotlin/com/google/samples/apps/nowinandroid/core/network/NiaNetworkDataSource.kt */
interface FitnessNetworkDataSource {

    suspend fun sendCode(request: NetworkSendCodeRequest): NetworkMessage

    suspend fun login(request: NetworkLoginRequest): NetworkLoginResponse

    suspend fun logout(): String

    suspend fun getTargets(): List<NetworkTarget>

    suspend fun getProfile(): NetworkProfile

    suspend fun updateName(request: NetworkUpdateNameRequest): NetworkProfile

    suspend fun getSubscriptionPacks(): List<NetworkPack>

    suspend fun getModules(packId: Int): List<NetworkModule>

    suspend fun getLessons(moduleId: Int): List<NetworkLesson>

    suspend fun getRandomFreeLessons(): List<NetworkLesson>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): List<NetworkLesson>

    suspend fun getLesson(lessonId: Int): NetworkLesson

    suspend fun getMyOrders(): List<NetworkOrder>

    suspend fun getModulesByOrderId(orderId: Int): List<NetworkOrderModule>
}