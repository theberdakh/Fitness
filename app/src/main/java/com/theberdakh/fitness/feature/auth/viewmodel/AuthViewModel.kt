package com.theberdakh.fitness.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.data.network.NetworkMessage
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.data.preferences.FitnessPreferences
import com.theberdakh.fitness.data.preferences.LocalUserSession
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.converter.toGoalPosters
import com.theberdakh.fitness.domain.converter.toLocalUserPreference
import com.theberdakh.fitness.domain.model.UserPreference
import com.theberdakh.fitness.feature.auth.adapter.GoalPoster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(
    private val repository: FitnessRepository,
    private val preferences: FitnessPreferences,
) : ViewModel() {

    fun sendCode(phone: String) = sendCodeUiState(phone, repository).stateIn(
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

    val goalsUiState = getGoalsUiState(repository).stateIn(
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
        is Result.Error -> emit(LogOutUiState.Error)
        Result.Loading -> emit(LogOutUiState.Loading)
        is Result.Success -> {
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

private fun getProfileUiState(repository: FitnessRepository, preferences: FitnessPreferences) =
    flow {
        when (val result = repository.getProfile()) {
            is Result.Error -> emit(GetProfileUiState.Error)
            Result.Loading -> emit(GetProfileUiState.Loading)
            is Result.Success -> {
                preferences.saveUserData(
                    preferences.getUserData().toDomain().copy(
                        name = result.data.name,
                        phone = result.data.phone,
                        goalId = result.data.targetId ?: UserPreference.NO_GOAL_ID
                    ).toLocalUserPreference()
                )

                preferences.getUserData()
                emit(GetProfileUiState.Success(result.data))
            }
        }
    }

sealed interface GetProfileUiState {
    data class Success(val data: NetworkProfile) : GetProfileUiState
    data object Error : GetProfileUiState
    data object Loading : GetProfileUiState
}

private fun getGoalsUiState(repository: FitnessRepository) =
    flow {
        when (val result = repository.getTargets()) {
            is Result.Error -> emit(GetTargetsUiState.Error)
            Result.Loading -> emit(GetTargetsUiState.Loading)
            is Result.Success -> { emit(GetTargetsUiState.Success(result.data.map { it.toDomain() }.toGoalPosters())) }
        }
    }

sealed interface GetTargetsUiState {
    data class Success(val data: List<GoalPoster>) : GetTargetsUiState
    data object Error : GetTargetsUiState
    data object Loading : GetTargetsUiState
}

private fun updateNameUiState(name: String, repository: FitnessRepository) = flow {
    when (val result = repository.updateName(NetworkUpdateNameRequest(name))) {
        is Result.Error -> emit(UpdateNameUiState.Error(result.message))
        Result.Loading -> emit(UpdateNameUiState.Loading)
        is Result.Success -> {
            emit(UpdateNameUiState.Success(result.data))
        }
    }
}

sealed interface UpdateNameUiState {
    data class Success(val data: NetworkProfile) : UpdateNameUiState
    data class Error(val errorMessage: String) : UpdateNameUiState
    data object Loading : UpdateNameUiState
}

private fun loginState(
    phone: String,
    code: String,
    repository: FitnessRepository,
    preferences: FitnessPreferences
) = flow {
    emit(LoginUiState.Loading)
    when (val result =
        repository.login(NetworkLoginRequest(phone = phone, verificationCode = code))) {
        is Result.Error -> emit(LoginUiState.Error(result.message))
        Result.Loading -> emit(LoginUiState.Loading)
        is Result.Success -> {
            preferences.saveUserData(result.data.toDomain().toLocalUserPreference())
            preferences.saveUserSession(
                LocalUserSession(
                    accessToken = result.data.accessToken,
                    tokenType = result.data.tokenType,
                    isLoggedIn = true
                )
            )
            emit(LoginUiState.Success)
        }
    }
}

sealed interface LoginUiState {
    data object Success : LoginUiState
    data class Error(val msg: String) : LoginUiState
    data object Loading : LoginUiState
}

private fun sendCodeUiState(
    phoneNumber: String,
    repository: FitnessRepository
): Flow<SendCodeUiState> =
    flow {
        emit(SendCodeUiState.Loading)
        when (val result = repository.sendCode(NetworkSendCodeRequest(phoneNumber))) {
            is Result.Error -> emit(SendCodeUiState.Error(result.message))
            Result.Loading -> emit(SendCodeUiState.Loading)
            is Result.Success -> {
                emit(SendCodeUiState.Success(result.data))
            }
        }
    }

sealed interface SendCodeUiState {
    data class Success(val data: NetworkMessage) : SendCodeUiState
    data class Error(val error: String) : SendCodeUiState
    data object Loading : SendCodeUiState
}
