package com.theberdakh.fitness.domain

import com.theberdakh.fitness.data.network.NetworkMessage
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.data.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest

interface FitnessRepository {

    suspend fun sendCode(request: NetworkSendCodeRequest): Result<NetworkMessage>

    suspend fun login(request: NetworkLoginRequest): Result<NetworkLoginResponse>

    suspend fun logout(): Result<String>

    suspend fun getTargets(): Result<List<NetworkTarget>>

    suspend fun getProfile(): Result<NetworkProfile>

    suspend fun updateName(request: NetworkUpdateNameRequest): Result<NetworkProfile>

    suspend fun getSubscriptionPacks(): Result<List<NetworkPack>>

    suspend fun getModules(packId: Int): Result<List<NetworkModule>>

    suspend fun getModulesByOrderId(orderId: Int): Result<List<NetworkOrderModule>>

    suspend fun getLessons(moduleId: Int): Result<List<NetworkLesson>>

    suspend fun getRandomFreeLessons(): Result<List<NetworkLesson>>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): Result<List<NetworkLesson>>

    suspend fun getLesson(lessonId: Int): Result<NetworkLesson>

    suspend fun getMyOrders(): Result<List<NetworkOrder>>
}