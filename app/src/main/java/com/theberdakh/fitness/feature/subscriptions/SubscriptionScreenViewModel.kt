package com.theberdakh.fitness.feature.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toSubscriptionPackItems
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SubscriptionScreenViewModel(repository: FitnessRepository) : ViewModel() {

    val subscriptionPacksUiState = subscriptionPackState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SubscriptionUiState.Loading
    )
}

private fun subscriptionPackState(repository: FitnessRepository) = flow {
    emit(SubscriptionUiState.Loading)
    when (val result = repository.getSubscriptionPacks()) {
        is Result.Error -> emit(SubscriptionUiState.Error)
        is Result.Success -> emit(SubscriptionUiState.Success(result.data.toSubscriptionPackItems()))
    }
}

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState
    data class Success(val subscriptionPacks: List<SubscriptionPackItem>) : SubscriptionUiState
    data object Error : SubscriptionUiState
}