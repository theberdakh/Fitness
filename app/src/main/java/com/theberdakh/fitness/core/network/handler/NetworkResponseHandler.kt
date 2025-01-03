package com.theberdakh.fitness.core.network.handler

import android.util.Log
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.model.BaseNetworkModel
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.PagingResponse
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

    protected fun <T> handleMessage(
        apiCall: suspend () -> Response<MessageModel>
    ) = flow {
        val response = apiCall()
        if (response.isSuccessful){
            Log.i(TAG, "sendCode: body: ${response.body()}, message: ${response.message()}, code: ${response.code()}")
            emit(NetworkResponse.Success(response.body()!!))
        } else {
            emit( NetworkResponse.Error(response.errorBody().toString()))
        }
    }.flowOn(Dispatchers.IO)

    protected fun <T> handleNetworkResponseOnlyMessage(
        apiCall: suspend () -> Response<MessageModel>
    ) = flow {
        emit(NetworkResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(NetworkResponse.Success(response.body()?.message ?: ""))
            } else {
                emit(NetworkResponse.Error(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    protected fun <T> handlePagingResponse(
        apiCall: suspend () -> Response<PagingResponse<T>>
    ) = flow{
        emit(NetworkResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResponse.Success(it))
                }
            } else {
                emit(NetworkResponse.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message ?: "Unknown error"))
        }
    }

}