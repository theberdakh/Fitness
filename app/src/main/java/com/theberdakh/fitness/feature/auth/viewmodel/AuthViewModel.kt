package com.theberdakh.fitness.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkMessage
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.core.data.source.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.core.domain.converter.toDomain
import com.theberdakh.fitness.core.preferences.FitnessPreferences
import com.theberdakh.fitness.feature.auth.model.GoalPoster
import com.theberdakh.fitness.feature.common.network.NetworkStateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(
    private val repository: FitnessRepository,
    private val preferences: FitnessPreferences,
    private val networkStateManager: NetworkStateManager
) : ViewModel() {

    fun sendCode(code: String) = sendCodeUiState(code, repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SendCodeUiState.Loading
    )

    fun login(phone: String, code: String) =
        loginState(phone, code, repository, preferences).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoginUiState.Loading
        )

    fun updateName(name: String) = updateNameUiState(name, repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UpdateNameUiState.Loading
    )

    val targetsUiState = getTargetsUiState(repository, preferences).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GetTargetsUiState.Loading
    )

    val getProfileUiState = getProfileUiState(repository, preferences).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GetProfileUiState.Loading
    )

    val logOutUiState = logOutUiState(repository, preferences).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LogOutUiState.Loading
    )
}

private fun logOutUiState(repository: FitnessRepository, preferences: FitnessPreferences) = flow {
    emit(LogOutUiState.Loading)
    when (val result = repository.logout()) {
        is NetworkResult.Error -> emit(LogOutUiState.Error)
        is NetworkResult.Success -> {
            preferences.clear()
            emit(LogOutUiState.Success(result.data))
        }
    }
}

sealed interface LogOutUiState {
    data class Success(val data: String) : LogOutUiState
    data object Error : LogOutUiState
    data object Loading : LogOutUiState
}

private fun getProfileUiState(repository: FitnessRepository, preferences: FitnessPreferences) = flow {

    fun saveProfileToPreferences(data: NetworkProfile): Boolean {
        data.name.let { preferences.userName = it }
        data.phone.let { preferences.userPhone = it }
        data.targetId?.let { preferences.userTargetId = it }
        return true
    }

    when (val result = repository.getProfile()) {
        is NetworkResult.Error -> emit(GetProfileUiState.Error)
        is NetworkResult.Success -> {
            preferences.isUserLoggedIn = true
            saveProfileToPreferences(result.data)
            emit(GetProfileUiState.Success(result.data))
        }
    }
}

sealed interface GetProfileUiState {
    data class Success(val data: NetworkProfile) : GetProfileUiState
    data object Error : GetProfileUiState
    data object Loading : GetProfileUiState
}

private fun getTargetsUiState(repository: FitnessRepository, preferences: FitnessPreferences) = flow {
    when (val result = repository.getTargets()) {
        is NetworkResult.Error -> emit(GetTargetsUiState.Error)
        is NetworkResult.Success -> {
            preferences.isUserLoggedIn = true
            emit(GetTargetsUiState.Success(result.data.map { it.toDomain() }))
        }
    }
}

sealed interface GetTargetsUiState {
    data class Success(val data: List<GoalPoster>) : GetTargetsUiState
    data object Error : GetTargetsUiState
    data object Loading : GetTargetsUiState
}

private fun updateNameUiState(name: String, repository: FitnessRepository) = flow {
    when (val result = repository.updateName(NetworkUpdateNameRequest(name))) {
        is NetworkResult.Error -> emit(UpdateNameUiState.Error)
        is NetworkResult.Success -> emit(UpdateNameUiState.Success(result.data))
    }
}

sealed interface UpdateNameUiState {
    data class Success(val data: NetworkProfile) : UpdateNameUiState
    data object Error : UpdateNameUiState
    data object Loading : UpdateNameUiState
}

private fun loginState(
    phone: String,
    code: String,
    repository: FitnessRepository,
    preferences: FitnessPreferences
) = flow {

    fun saveDataToPreferences(data: NetworkLoginResponse): Boolean {
        data.accessToken?.let { token -> preferences.accessToken = token }
        data.tokenType?.let { tokenType -> preferences.tokenType = tokenType }
        data.user?.let { user ->
            user.id?.let { id -> preferences.userId = id }
            user.name?.let { name -> preferences.userName = name }
            user.phone?.let { phone -> preferences.userPhone = phone }
            user.targetId?.let { targetId -> preferences.userTargetId = targetId }
        }
        return true
    }
    emit(LoginUiState.Loading)
    when (val result =
        repository.login(NetworkLoginRequest(phone = phone, verificationCode = code))) {
        is NetworkResult.Error -> emit(LoginUiState.Error)
        is NetworkResult.Success -> {
            if (saveDataToPreferences(data = result.data)) {
                emit(LoginUiState.Success(result.data))
            } else {
                emit(LoginUiState.Loading)
            }
        }
    }
}

sealed interface LoginUiState {
    data class Success(val data: NetworkLoginResponse) : LoginUiState
    data object Error : LoginUiState
    data object Loading : LoginUiState
}

private fun sendCodeUiState(code: String, repository: FitnessRepository): Flow<SendCodeUiState> =
    flow {
        emit(SendCodeUiState.Loading)
        when (val result = repository.sendCode(NetworkSendCodeRequest(""))) {
            is NetworkResult.Error -> emit(SendCodeUiState.Error)
            is NetworkResult.Success -> emit(SendCodeUiState.Success(result.data))
        }
    }

sealed interface SendCodeUiState {
    data class Success(val data: NetworkMessage) : SendCodeUiState
    data object Error : SendCodeUiState
    data object Loading : SendCodeUiState
}
