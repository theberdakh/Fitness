package com.theberdakh.fitness.feature.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.feature.notification.model.NotificationItem
import com.theberdakh.fitness.feature.notification.model.toNotificationItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import com.theberdakh.fitness.domain.Result

class NotificationViewModel(repository: FitnessRepository) : ViewModel() {

    val notifications = getAllNotificationsUiState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NotificationUiState.Loading
    )

}

private fun getAllNotificationsUiState(repository: FitnessRepository) = flow {
    when (val result = repository.getNotifications()) {
        is Result.Error -> {
            emit(NotificationUiState.Error(result.message))
        }
        is Result.Success -> {
            emit(NotificationUiState.Success(result.data.map { it.toNotificationItem() }))
        }
    }
}

sealed interface NotificationUiState {
    data object Loading : NotificationUiState
    data class Error(val message: String) : NotificationUiState
    data class Success(val data: List<NotificationItem>) : NotificationUiState
}