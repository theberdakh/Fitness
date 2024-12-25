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
import com.theberdakh.fitness.core.network.model.mobile.toDomain
import com.theberdakh.fitness.core.preferences.FitnessPreferences
import com.theberdakh.fitness.feature.auth.model.GoalPoster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: NetworkFitnessRepository,
    private val preferences: FitnessPreferences
) : ViewModel() {

    private val _sendCodeState = MutableStateFlow<NetworkResponse<MessageModel>>(NetworkResponse.Initial)
    val sendCodeState = _sendCodeState.asStateFlow()

    fun sendCode(phone: String) = viewModelScope.launch {
        _sendCodeState.value = NetworkResponse.Loading
        repository.sendCode(SendCodeRequestBody(phone)).onEach {
            _sendCodeState.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading ->  NetworkResponse.Loading
                is NetworkResponse.Success -> NetworkResponse.Success(it.data)
            }
        }.launchIn(viewModelScope)
    }

    fun resetSendCodeState() {
        _sendCodeState.value = NetworkResponse.Initial
    }

    private val _loginState = MutableStateFlow<NetworkResponse<LoginResponse>>(NetworkResponse.Initial)
    val loginState = _loginState.asStateFlow()
    fun login(phone: String, code: String) = viewModelScope.launch {
        _loginState.value = NetworkResponse.Loading
        repository.login(LoginRequestBody(phone = phone, verificationCode = code)).onEach {
            _loginState.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                is NetworkResponse.Success -> {
                    if (saveDataToPreferences(it.data)) {
                        NetworkResponse.Success(it.data)
                    } else {
                        NetworkResponse.Loading
                    }
                }
                NetworkResponse.Loading -> NetworkResponse.Loading
                NetworkResponse.Initial -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }

    private fun saveDataToPreferences(data: LoginResponse): Boolean {
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
        return true
    }

    private val _updateNameState = MutableStateFlow<NetworkResponse<Profile>>(NetworkResponse.Initial)
    val updateNameState = _updateNameState.asStateFlow()

    fun updateName(name: String) = viewModelScope.launch {
        _updateNameState.value = NetworkResponse.Loading
        repository.updateName(name).onEach {
           _updateNameState.value =  when (it) {
               is NetworkResponse.Error -> NetworkResponse.Error(it.message)
               NetworkResponse.Initial -> NetworkResponse.Initial
               NetworkResponse.Loading -> NetworkResponse.Loading
               is NetworkResponse.Success -> NetworkResponse.Success(it.data)
           }
        }.launchIn(viewModelScope)
    }

    fun resetUpdateNameState() {
        _updateNameState.value = NetworkResponse.Initial
    }

    private val _getTargetsState = MutableStateFlow<NetworkResponse<List<GoalPoster>>>(NetworkResponse.Initial)
    val getTargetsState = _getTargetsState.asStateFlow()

    fun getTargets() = viewModelScope.launch {
        _getTargetsState.value = NetworkResponse.Loading
        repository.getTargets().onEach { targets ->
            _getTargetsState.value = when (targets) {
                is NetworkResponse.Error ->  NetworkResponse.Error(targets.message)
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> {
                    preferences.isUserLoggedIn = true
                    NetworkResponse.Success(targets.data.map { it.toDomain() })
                }
                NetworkResponse.Initial -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }

    private val _getProfileState = MutableStateFlow<NetworkResponse<Profile>>(NetworkResponse.Initial)
    val getProfileState = _getProfileState.asStateFlow()
    fun getProfile() = viewModelScope.launch {
        repository.getProfile().onEach {
          _getProfileState.value = when(it) {
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> {
                    if (saveProfileToPreferences(it.data)) {
                        NetworkResponse.Success(it.data)
                    } else {
                        NetworkResponse.Loading
                    }
                }
                NetworkResponse.Initial -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }

    private fun saveProfileToPreferences(data: Profile): Boolean {
        data.name.let { preferences.userName = it }
        data.phone.let { preferences.userPhone = it }
        data.targetId?.let { preferences.userTargetId = it }
        return true
    }


    fun logout() = viewModelScope.launch {
        repository.logout().onEach {
            when(it) {
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> {
                    preferences.clear()
                    NetworkResponse.Success(it.data)
                }
                NetworkResponse.Initial -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }
}
