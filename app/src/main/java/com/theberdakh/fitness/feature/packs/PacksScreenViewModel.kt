package com.theberdakh.fitness.feature.packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toPackListItems
import com.theberdakh.fitness.feature.packs.model.PackListItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PacksScreenViewModel(repository: FitnessRepository) : ViewModel() {
    val subscriptionPacksUiState = subscriptionPackState(repository).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = SubscriptionUiState.Loading
    )
}

private fun subscriptionPackState(repository: FitnessRepository) = flow {
    emit(SubscriptionUiState.Loading)
    when (val result = repository.getMyOrders()) {
        is Result.Error -> emit(SubscriptionUiState.Error(result.message))
        is Result.Success -> { emit(SubscriptionUiState.Success(result.data.toPackListItems())) }
    }
}

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState
    data class Success(val subscriptionPacks: List<PackListItem>) : SubscriptionUiState
    data class Error(val message: String) : SubscriptionUiState
}