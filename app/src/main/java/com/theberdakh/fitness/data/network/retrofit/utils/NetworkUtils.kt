package com.theberdakh.fitness.data.network.retrofit.utils

import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.NetworkServerError
import com.theberdakh.fitness.data.network.NetworkServerMessage
import com.theberdakh.fitness.data.network.model.ServerResponse
import kotlinx.serialization.json.Json
import retrofit2.Response

private const val ERROR_MESSAGE_EMPTY_RESPONSE_BODY = "Empty response body"
private const val ERROR_MESSAGE_UNAUTHORISED = "Unauthorized"

private const val ERROR_CODE_CUSTOM_ERROR = 422
private const val ERROR_CODE_UNAUTHORISED = 401

/**
 * Simple wrapper to make request and wrap to NetworkResult
 * */
object NetworkUtils {
    suspend fun <T> makeRequest(apiCall: suspend () -> Response<T>): NetworkResult<T> = try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                val responseBody = it
                NetworkResult.Success(data = responseBody)
            } ?: NetworkResult.Error(ERROR_MESSAGE_EMPTY_RESPONSE_BODY)
        } else {
            val errorBody = response.errorBody()?.string()
            when (response.code()) {
                ERROR_CODE_CUSTOM_ERROR -> {
                    errorBody?.let {
                        val networkServerError = Json.decodeFromString<NetworkServerError>(it)
                        NetworkResult.Error(networkServerError.message)
                    } ?: NetworkResult.Error(message = customErrorMessage(response))
                }

                ERROR_CODE_UNAUTHORISED -> NetworkResult.Error(message = ERROR_MESSAGE_UNAUTHORISED)
                else -> {
                    errorBody?.let {
                        val networkServerError = Json.decodeFromString<NetworkServerMessage>(it)
                        NetworkResult.Error(networkServerError.message)
                    }
                        ?: NetworkResult.Error(message = customErrorMessage(response))
                }
            }
        }
    } catch (e: Exception) {
        NetworkResult.Error(message = e.message.toString(), exception = e)
    }

    /** With this extension function, NetworkDataSource no longer dependent on ServerResponse.
     * This also prevents confusing calls like data.data. on ViewModel layer
     * */
    fun <T> NetworkResult<ServerResponse<T>>.unwrap(): NetworkResult<T> {
        return when (this) {
            is NetworkResult.Success -> NetworkResult.Success(data = this.data.data)
            is NetworkResult.Error -> NetworkResult.Error(
                message = this.message,
                exception = this.exception
            )
        }
    }

    private fun <T> customErrorMessage(response: Response<T>) =
        "Error ${response.code()}: ${response.message()}"

}