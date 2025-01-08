package com.theberdakh.fitness.core.data.source.network

import com.theberdakh.fitness.core.data.source.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.data.source.network.model.MessageModel
import com.theberdakh.fitness.core.data.source.network.model.PagingResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.Module
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkPack
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest
import retrofit2.Response

/*Inspired from https://github.com/android/nowinandroid/blob/main/core/network/src/main/kotlin/com/google/samples/apps/nowinandroid/core/network/NiaNetworkDataSource.kt */
interface FitnessNetworkDataSource {

    suspend fun sendCode(request: NetworkSendCodeRequest): MessageModel

    suspend fun login(request: NetworkLoginRequest): NetworkLoginResponse

    suspend fun logout(): String

    suspend fun getTargets(): List<Target>

    suspend fun getProfile(): NetworkProfile

    suspend fun updateName(request: NetworkUpdateNameRequest): NetworkProfile

    suspend fun getSubscriptionPacks(): List<NetworkPack>

    suspend fun getModules(packId: Int): List<Module>

    suspend fun getLessons(moduleId: Int): List<NetworkLesson>

    suspend fun getRandomFreeLessons(): List<NetworkLesson>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): PagingResponse<NetworkLesson>

    suspend fun getLesson(lessonId: Int): NetworkLesson

    suspend fun getMyOrders(): List<NetworkOrder>
}