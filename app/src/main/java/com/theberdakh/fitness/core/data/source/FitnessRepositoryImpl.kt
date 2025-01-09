package com.theberdakh.fitness.core.data.source

import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest

class FitnessRepositoryImpl(private val networkDataSource: FitnessNetworkDataSource) : FitnessRepository {

    override suspend fun sendCode(request: NetworkSendCodeRequest) = networkDataSource.sendCode(request)

    override suspend fun login(request: NetworkLoginRequest) = networkDataSource.login(request)

    override suspend fun logout() = networkDataSource.logout()

    override suspend fun getTargets() = networkDataSource.getTargets()

    override suspend fun getProfile() =networkDataSource.getProfile()

    override suspend fun updateName(request: NetworkUpdateNameRequest) =networkDataSource.updateName(request)

    override suspend fun getSubscriptionPacks() =  networkDataSource.getSubscriptionPacks()

    override suspend fun getModules(packId: Int) = networkDataSource.getModules(packId)

    override suspend fun getModulesByOrderId(orderId: Int) =  networkDataSource.getModulesByOrderId(orderId)

    override suspend fun getLessons(moduleId: Int) = networkDataSource.getLessons(moduleId)

    override suspend fun getRandomFreeLessons() =  networkDataSource.getRandomFreeLessons()

    override suspend fun getFreeLessons(perPage: Int, cursor: String?) =  networkDataSource.getFreeLessons(perPage, cursor)

    override suspend fun getLesson(lessonId: Int) = networkDataSource.getLesson(lessonId)

    override suspend fun getMyOrders(): NetworkResult<List<NetworkOrder>> = networkDataSource.getMyOrders()

}