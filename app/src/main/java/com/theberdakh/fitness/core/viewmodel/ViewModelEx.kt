package com.theberdakh.fitness.core.viewmodel

import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.mobile.Module
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object ViewModelEx {

  /*  fun <T>makeRequest(stateFlow: MutableStateFlow<NetworkResponse<T>>, request: Flow<NetworkResponse<T>>, coroutineScope: CoroutineScope) {
        stateFlow.value = NetworkResponse.Loading
        coroutineScope.launch {
            request.invoke().let {
                it.onEach {
                    stateFlow.value = when(it){
                        is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                        NetworkResponse.Initial -> NetworkResponse.Initial
                        NetworkResponse.Loading ->  NetworkResponse.Loading
                        is NetworkResponse.Success -> NetworkResponse.Success(it.data)
                    }
                }
            }
        }
    }*/
}