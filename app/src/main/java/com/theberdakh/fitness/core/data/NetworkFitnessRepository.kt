package com.theberdakh.fitness.core.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonPrimitive
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.core.network.api.FitnessNetworkApi
import com.theberdakh.fitness.core.network.handler.NetworkResponseHandler
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class NetworkFitnessRepository(private val api: FitnessNetworkApi) :
    NetworkResponseHandler() {


    fun sendCode(body: SendCodeRequestBody) = handleMessage<NetworkResponse<MessageModel>> { api.sendCode(body) }

    fun login(body: LoginRequestBody) = handleNetworkResponse { api.login(body) }

    fun logout() = handleNetworkResponse { api.logout() }

    fun getTargets() = handleNetworkResponse { api.getTargets() }

    fun getProfile() = handleNetworkResponse { api.getProfile() }

}