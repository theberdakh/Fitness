package com.theberdakh.fitness.feature.notification_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.feature.notification_details.model.NotificationDetailsItem
import com.theberdakh.fitness.feature.notification_details.model.toNotificationDetailsItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class NotificationDetailsScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    fun getNotification(notificationId: Int) = getNotificationUiState(notificationId, repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = NotificationDetailsScreenUiState.Loading
    )
}

private fun getNotificationUiState(notificationId: Int, repository: FitnessRepository) = flow {
    when(val result = repository.getNotification(notificationId)) {
        is Result.Success -> {
            emit(NotificationDetailsScreenUiState.Success(result.data.toNotificationDetailsItem()))
        }
        is Result.Error -> {
            emit(NotificationDetailsScreenUiState.Error(result.message))
        }
    }
}

sealed interface NotificationDetailsScreenUiState {
    data class Success(val data: NotificationDetailsItem) : NotificationDetailsScreenUiState
    data class Error(val message: String) : NotificationDetailsScreenUiState
    data object Loading : NotificationDetailsScreenUiState
}