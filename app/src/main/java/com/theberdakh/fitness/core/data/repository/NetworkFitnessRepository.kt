package com.theberdakh.fitness.core.data.repository

import com.theberdakh.fitness.core.data.network.api.FitnessNetworkApi
import com.theberdakh.fitness.core.data.network.handler.NetworkResponseHandler

class NetworkFitnessRepository(private val api: FitnessNetworkApi) : FitnessRepository,
    NetworkResponseHandler() {

    override suspend fun sendCode() = handleNetworkResponse { api.sendCode() }

    override suspend fun login() = handleNetworkResponse { api.login() }

    override suspend fun logout() = handleNetworkResponse { api.logout() }

}