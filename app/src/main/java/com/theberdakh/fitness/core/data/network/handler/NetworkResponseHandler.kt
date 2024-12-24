package com.theberdakh.fitness.core.data.network.handler

import com.theberdakh.fitness.core.data.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.data.network.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class NetworkResponseHandler {
    protected fun <T> handleNetworkResponse(
        apiCall: suspend () -> Response<BaseNetworkModel<T>>
    ) = flow {
        emit(NetworkResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    emit(NetworkResponse.Success(it))
                }
            } else {
                emit(NetworkResponse.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}