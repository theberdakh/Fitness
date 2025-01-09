package com.theberdakh.fitness.feature.packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.domain.converter.toPackListItem
import com.theberdakh.fitness.core.domain.converter.toSubscriptionPackItem
import com.theberdakh.fitness.feature.packs.model.PackListItem
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PacksScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    val subscriptionPacksUiState = subscriptionPackState(repository).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = SubscriptionUiState.Loading
    )

}

private fun subscriptionPackState(repository: FitnessRepository) = flow {
    when (val result = repository.getMyOrders()) {
        is NetworkResult.Error -> emit(SubscriptionUiState.Error)
        is NetworkResult.Success -> {
            emit(SubscriptionUiState.Success(result.data.map { order ->
                order.toPackListItem()
            }))
        }
    }
}

sealed interface SubscriptionUiState {
    data object Loading : SubscriptionUiState
    data class Success(val subscriptionPacks: List<PackListItem>) : SubscriptionUiState
    data object Error : SubscriptionUiState
}