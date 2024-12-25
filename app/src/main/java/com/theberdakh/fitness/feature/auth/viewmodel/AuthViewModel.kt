package com.theberdakh.fitness.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.network.model.MessageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: NetworkFitnessRepository) : ViewModel() {

    private val _sendCodeState = MutableStateFlow<NetworkResponse<MessageModel>?>(null)
    val sendCodeState = _sendCodeState.asStateFlow()

    fun sendCode(phone: String) = viewModelScope.launch {
        _sendCodeState.value = NetworkResponse.Loading
        repository.sendCode(SendCodeRequestBody(phone)).onEach {
            _sendCodeState.value = it
        }.launchIn(viewModelScope)
    }


}