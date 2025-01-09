package com.theberdakh.fitness.core.data.source

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

interface FitnessRepository {

    suspend fun sendCode(request: NetworkSendCodeRequest): NetworkResult<NetworkMessage>

    suspend fun login(request: NetworkLoginRequest): NetworkResult<NetworkLoginResponse>

    suspend fun logout(): NetworkResult<String>

    suspend fun getTargets(): NetworkResult<List<NetworkTarget>>

    suspend fun getProfile(): NetworkResult<NetworkProfile>

    suspend fun updateName(request: NetworkUpdateNameRequest): NetworkResult<NetworkProfile>

    suspend fun getSubscriptionPacks(): NetworkResult<List<NetworkPack>>

    suspend fun getModules(packId: Int): NetworkResult<List<NetworkModule>>

    suspend fun getModulesByOrderId(orderId: Int): NetworkResult<List<NetworkOrderModule>>

    suspend fun getLessons(moduleId: Int): NetworkResult<List<NetworkLesson>>

    suspend fun getRandomFreeLessons(): NetworkResult<List<NetworkLesson>>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): NetworkResult<List<NetworkLesson>>

    suspend fun getLesson(lessonId: Int): NetworkResult<NetworkLesson>

    suspend fun getMyOrders(): NetworkResult<List<NetworkOrder>>
}