package com.theberdakh.fitness.feature.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.data.preferences.LocalUserPreference
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toGoalPosters
import com.theberdakh.fitness.domain.converter.toLocalUserPreference
import com.theberdakh.fitness.feature.auth.adapter.GoalPoster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(private val repository: FitnessRepository) : ViewModel() {

    fun sendCode(phone: String) = sendCodeUiState(phone, repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SendCodeUiState.Loading
    )

    fun login(phone: String, code: String) =
        loginState(phone, code, repository).stateIn(
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

    val getProfileUiState = getProfileUiState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = GetProfileUiState.Loading
    )

    val logOutUiState = logOutUiState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LogOutUiState.Loading
    )
}

private fun logOutUiState(repository: FitnessRepository) = flow {
    emit(LogOutUiState.Loading)
    when (val result = repository.logout()) {
        is Result.Error -> emit(LogOutUiState.Error)
        is Result.Success -> emit(LogOutUiState.Success(result.data))
    }
}

sealed interface LogOutUiState {
    data class Success(val data: String) : LogOutUiState
    data object Error : LogOutUiState
    data object Loading : LogOutUiState
}

private fun getProfileUiState(repository: FitnessRepository) =
    flow {
        emit(GetProfileUiState.Loading)
        when (val result = repository.getProfile()) {
            is Result.Error -> {
                emit(GetProfileUiState.Error(result.message))
            }
            is Result.Success -> {
                emit(GetProfileUiState.Success(result.data.toLocalUserPreference()))
            }
        }
    }

sealed interface GetProfileUiState {
    data class Success(val data: LocalUserPreference) : GetProfileUiState
    data class Error(val message: String) : GetProfileUiState
    data object Loading : GetProfileUiState
}

private fun getGoalsUiState(repository: FitnessRepository) =
    flow {
        emit(GetTargetsUiState.Loading)
        when (val result = repository.getGoals()) {
            is Result.Error -> emit(GetTargetsUiState.Error)
            is Result.Success -> {
                emit(GetTargetsUiState.Success(result.data.toGoalPosters()))
            }
        }
    }

sealed interface GetTargetsUiState {
    data class Success(val data: List<GoalPoster>) : GetTargetsUiState
    data object Error : GetTargetsUiState
    data object Loading : GetTargetsUiState
}

private fun updateNameUiState(name: String, repository: FitnessRepository) = flow {
    emit(UpdateNameUiState.Loading)
    when (val result = repository.updateName(NetworkUpdateNameRequest(name))) {
        is Result.Error -> emit(UpdateNameUiState.Error(result.message))
        is Result.Success -> {
            emit(UpdateNameUiState.Success)
        }
    }
}

sealed interface UpdateNameUiState {
    data object Success : UpdateNameUiState
    data class Error(val message: String) : UpdateNameUiState
    data object Loading : UpdateNameUiState
}

private fun loginState(
    phone: String,
    code: String,
    repository: FitnessRepository
) = flow {
    emit(LoginUiState.Loading)
    Log.i("LoginUiState", "loginState: Called")
    when (val result = repository.login(NetworkLoginRequest(phone = phone, verificationCode = code))) {
        is Result.Error -> {
            Log.i("LoginUiState", "loginState: Error ${result.message}")
            emit(LoginUiState.Error(result.message))
        }
        is Result.Success -> {
            Log.i("LoginUiState", "loginState: Success ${result.data}")
            emit(LoginUiState.Success)
        }
    }
}

sealed interface LoginUiState {
    data object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
    data object Loading : LoginUiState
}

private fun sendCodeUiState(
    phoneNumber: String,
    repository: FitnessRepository
) = flow {
        emit(SendCodeUiState.Loading)
        when (val result = repository.sendCode(NetworkSendCodeRequest(phoneNumber))) {
            is Result.Error -> {
                Log.i("SendPhoneNumber", "sendCodeUiState: ${result.message}")
                emit(SendCodeUiState.Error(result.message))
            }
            is Result.Success -> {
                Log.i("SendPhoneNumber", "sendCodeUiState: ${result.data}")
                emit(SendCodeUiState.Success(result.data))
            }
        }
    }

sealed interface SendCodeUiState {
    data class Success(val data: String) : SendCodeUiState
    data class Error(val error: String) : SendCodeUiState
    data object Loading : SendCodeUiState
}
