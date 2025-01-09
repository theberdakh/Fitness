package com.theberdakh.fitness.feature.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.domain.converter.toSubscriptionPackItem
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubscriptionScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    val subscriptionPacksUiState = subscriptionPackState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SubscriptionUiState.Loading
    )

}


private fun subscriptionPackState(repository: FitnessRepository) = flow {
    emit(SubscriptionUiState.Loading)
    when (val result = repository.getSubscriptionPacks()) {
        is NetworkResult.Error -> emit(SubscriptionUiState.Error)
        is NetworkResult.Success -> {
            emit(SubscriptionUiState.Success(result.data.map { subscription -> subscription.toSubscriptionPackItem() }))
        }
    }
}

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState
    data class Success(val subscriptionPacks: List<SubscriptionPackItem>) : SubscriptionUiState
    data object Error : SubscriptionUiState
}