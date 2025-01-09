package com.theberdakh.fitness.core.data.source

import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest

class FitnessRepositoryImpl(private val networkDataSource: FitnessNetworkDataSource) : FitnessRepository {

    override suspend fun sendCode(request: NetworkSendCodeRequest) = makeRequest { networkDataSource.sendCode(request) }

    override suspend fun login(request: NetworkLoginRequest) = makeRequest { networkDataSource.login(request) }

    override suspend fun logout() = makeRequest { networkDataSource.logout() }

    override suspend fun getTargets() = makeRequest { networkDataSource.getTargets() }

    override suspend fun getProfile() = makeRequest { networkDataSource.getProfile() }

    override suspend fun updateName(request: NetworkUpdateNameRequest) = makeRequest { networkDataSource.updateName(request) }

    override suspend fun getSubscriptionPacks() = makeRequest { networkDataSource.getSubscriptionPacks() }

    override suspend fun getModules(packId: Int) = makeRequest { networkDataSource.getModules(packId) }

    override suspend fun getModulesByOrderId(orderId: Int) = makeRequest { networkDataSource.getModulesByOrderId(orderId)}

    override suspend fun getLessons(moduleId: Int) = makeRequest { networkDataSource.getLessons(moduleId) }

    override suspend fun getRandomFreeLessons() = makeRequest { networkDataSource.getRandomFreeLessons() }

    override suspend fun getFreeLessons(perPage: Int, cursor: String?) = makeRequest { networkDataSource.getFreeLessons(perPage, cursor) }

    override suspend fun getLesson(lessonId: Int) = makeRequest { networkDataSource.getLesson(lessonId) }

    override suspend fun getMyOrders() = makeRequest { networkDataSource.getMyOrders() }
}