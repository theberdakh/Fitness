package com.theberdakh.fitness.core.data

import android.util.Log
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.api.FitnessNetworkApi
import com.theberdakh.fitness.core.network.handler.NetworkResponseHandler
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkFitnessRepository(private val api: FitnessNetworkApi) :
    NetworkResponseHandler() {

    fun sendCode(body: SendCodeRequestBody) = flow {
        emit(NetworkResponse.Loading)
        try {
            val response = api.sendCode(body)
            if (response.isSuccessful) {
                emit(NetworkResponse.Success(response.body()?.message ?: ""))
            } else {
                emit(NetworkResponse.Error(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "sendCode: ", e)
            emit(NetworkResponse.Error(e.message ?: "Unknown error"))
        }
    }

    fun login(body: LoginRequestBody) = handleNetworkResponse { api.login(body) }

    fun logout() = handleNetworkResponse { api.logout() }

    fun getTargets() = handleNetworkResponse { api.getTargets() }

    fun getProfile() = handleNetworkResponse { api.getProfile() }

}