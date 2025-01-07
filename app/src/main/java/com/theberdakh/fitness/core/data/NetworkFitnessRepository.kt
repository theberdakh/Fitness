package com.theberdakh.fitness.core.data

import com.theberdakh.fitness.core.network.api.FitnessNetworkApi
import com.theberdakh.fitness.core.network.handler.NetworkResponseHandler
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.core.network.model.mobile.UpdateNameRequestBody

class NetworkFitnessRepository(private val api: FitnessNetworkApi) :
    NetworkResponseHandler() {

    fun sendCode(body: SendCodeRequestBody) = handleMessage<NetworkResponse<MessageModel>> { api.sendCode(body) }

    fun login(body: LoginRequestBody) = handleNetworkResponse { api.login(body) }

    fun logout() = handleNetworkResponse { api.logout() }

    fun getTargets() = handleNetworkResponse { api.getTargets() }

    fun getProfile() = handleNetworkResponse { api.getProfile() }

    fun updateName(name: String) = handleNetworkResponse { api.updateName(UpdateNameRequestBody(name)) }

    fun getSubscriptionPacks() = handleNetworkResponse { api.getSubscriptionPacks() }

    fun getModules(packId: Int) = handleNetworkResponse { api.getModules(packId) }

    fun getLessons(moduleId: Int) = handleNetworkResponse { api.getLessons(moduleId) }

    fun getRandomFreeLessons() = handleNetworkResponse { api.getRandomFreeLessons() }

    fun getFreeLessons(perPage: Int, cursor: String?) = handlePagingResponse { api.getFreeLessons(perPage, cursor) }

    fun getLesson(lessonId: Int) = handleNetworkResponse { api.getLesson(lessonId) }

    fun getMyOrders() = handleNetworkResponse { api.getMyOrders() }

    fun getModulesByOrderId(orderId: Int) = handleNetworkResponse { api.getModulesByOrderId(orderId) }



}