package com.theberdakh.fitness.core.data

import com.theberdakh.fitness.core.data.source.network.api.FitnessNetworkApi
import com.theberdakh.fitness.core.data.source.network.handler.NetworkResponseHandler
import com.theberdakh.fitness.core.data.source.network.model.MessageModel
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest


class NetworkFitnessRepository(private val api: FitnessNetworkApi) :
    NetworkResponseHandler() {

    fun sendCode(body: NetworkSendCodeRequest) = handleMessage<NetworkResponse<MessageModel>> { api.sendCode(body) }

    fun login(body: NetworkLoginRequest) = handleNetworkResponse { api.login(body) }

    fun logout() = handleNetworkResponse { api.logout() }

    fun getTargets() = handleNetworkResponse { api.getTargets() }

    fun getProfile() = handleNetworkResponse { api.getProfile() }

    fun updateName(name: String) = handleNetworkResponse { api.updateName(NetworkUpdateNameRequest(name)) }

    fun getSubscriptionPacks() = handleNetworkResponse { api.getSubscriptionPacks() }

    fun getModules(packId: Int) = handleNetworkResponse { api.getModules(packId) }

    fun getLessons(moduleId: Int) = handleNetworkResponse { api.getLessons(moduleId) }

    fun getRandomFreeLessons() = handleNetworkResponse { api.getRandomFreeLessons() }

    fun getFreeLessons(perPage: Int, cursor: String?) = handlePagingResponse { api.getFreeLessons(perPage, cursor) }

    fun getLesson(lessonId: Int) = handleNetworkResponse { api.getLesson(lessonId) }

    fun getMyOrders() = handleNetworkResponse { api.getMyOrders() }

    fun getModulesByOrderId(orderId: Int) = handleNetworkResponse { api.getModulesByOrderId(orderId) }



}