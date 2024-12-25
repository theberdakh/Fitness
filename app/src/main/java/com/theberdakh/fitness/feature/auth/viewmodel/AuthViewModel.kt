package com.theberdakh.fitness.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.network.model.MessageModel
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.auth.LoginRequestBody
import com.theberdakh.fitness.core.network.model.auth.LoginResponse
import com.theberdakh.fitness.core.network.model.auth.SendCodeRequestBody
import com.theberdakh.fitness.core.network.model.mobile.Profile
import com.theberdakh.fitness.core.network.model.mobile.UpdateNameRequestBody
import com.theberdakh.fitness.core.preferences.FitnessPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: NetworkFitnessRepository,
    private val preferences: FitnessPreferences
) : ViewModel() {


    private val _sendCodeState = MutableStateFlow<NetworkResponse<MessageModel>?>(null)
    val sendCodeState = _sendCodeState.asStateFlow()

    fun sendCode(phone: String) = viewModelScope.launch {
        _sendCodeState.value = NetworkResponse.Loading
        repository.sendCode(SendCodeRequestBody(phone)).onEach {
            _sendCodeState.value = it
        }.launchIn(viewModelScope)
    }

    fun resetSendCodeState() {
        _sendCodeState.value = null
    }

    private val _loginState = MutableStateFlow<NetworkResponse<LoginResponse>?>(null)
    val loginState = _loginState.asStateFlow()
    fun login(phone: String, code: String) = viewModelScope.launch {
        _loginState.value = NetworkResponse.Loading
        repository.login(LoginRequestBody(phone = phone, verificationCode = code)).onEach {
            if (it is NetworkResponse.Success) {
                it.apply {
                    data.accessToken?.let { token ->
                        preferences.accessToken = token
                    }
                    data.tokenType?.let { tokenType ->
                        preferences.tokenType = tokenType
                    }

                    data.user?.let { user ->
                        user.id?.let { id -> preferences.userId = id }
                        user.name?.let { name -> preferences.userName = name }
                        user.phone?.let { phone -> preferences.userPhone = phone }
                        user.targetId?.let { targetId -> preferences.userTargetId = targetId }
                    }
                }
            }
            _loginState.value = it
        }.launchIn(viewModelScope)
    }

    private val _updateNameState = MutableStateFlow<NetworkResponse<Profile>?>(null)
    val updateNameState = _updateNameState.asStateFlow()

    fun updateName(name: String) = viewModelScope.launch {
        _updateNameState.value = NetworkResponse.Loading
        repository.updateName(name).onEach {
            if (it is NetworkResponse.Success) {
                preferences.userName = it.data.name
            }
            _updateNameState.value = it
        }.launchIn(viewModelScope)
    }
    fun resetUpdateNameState() {
        _updateNameState.value = null
    }


}