package com.theberdakh.fitness.data.network.retrofit


import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkMessageRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkNotificationDetail
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.data.network.retrofit.utils.NetworkUtils.makeRequest
import com.theberdakh.fitness.data.network.retrofit.utils.NetworkUtils.unwrap


/**
 * Retrofit API declaration for FitnessNetwork API
 * */
class RetrofitFitnessNetwork(private val api: FitnessNetworkApi) : FitnessNetworkDataSource {
    override suspend fun sendCode(request: NetworkSendCodeRequest) = makeRequest { api.sendCode(request) }
    override suspend fun login(request: NetworkLoginRequest) = makeRequest { api.login(request) }.unwrap()
    override suspend fun logout() = makeRequest { api.logout() }
    override suspend fun getTargets() = makeRequest { api.getTargets() }.unwrap()
    override suspend fun getProfile() = makeRequest { api.getProfile() }.unwrap()
    override suspend fun updateName(request: NetworkUpdateNameRequest) = makeRequest { api.updateName(request) }
    override suspend fun getSubscriptionPacks() = makeRequest { api.getSubscriptionPacks() }.unwrap()
    override suspend fun getModules(packId: Int) = makeRequest { api.getModules(packId) }.unwrap()
    override suspend fun getLessons(moduleId: Int) = makeRequest { api.getLessons(moduleId) }.unwrap()
    override suspend fun getRandomFreeLessons() = makeRequest { api.getRandomFreeLessons() }.unwrap()
    override suspend fun getFreeLessons(perPage: Int, cursor: String?) = makeRequest { api.getFreeLessons(perPage, cursor) }.unwrap()
    override suspend fun getLesson(lessonId: Int) = makeRequest { api.getLesson(lessonId) }.unwrap()
    override suspend fun getMyOrders() = makeRequest { api.getMyOrders() }.unwrap()
    override suspend fun getModulesByOrderId(orderId: Int) = makeRequest { api.getModulesByOrderId(orderId) }.unwrap()
    override suspend fun getNotifications() = makeRequest { api.getNotifications() }.unwrap()
    override suspend fun getNotification(notificationId: Int): NetworkResult<NetworkNotificationDetail> = makeRequest { api.getNotification(notificationId) }.unwrap()
    override suspend fun getMessages() = makeRequest { api.getMessages() }.unwrap()
    override suspend fun sendMessage(message: String) =  makeRequest { api.postMessage(NetworkMessageRequest(message)) }
    override suspend fun getAllOrders() = makeRequest { api.getAllOrders() }.unwrap()
}
