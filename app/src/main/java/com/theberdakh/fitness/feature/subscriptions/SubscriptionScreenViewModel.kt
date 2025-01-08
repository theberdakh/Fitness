package com.theberdakh.fitness.feature.subscriptions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.core.domain.converter.toSubscriptionPackItem
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.feature.subscriptions.model.SubscriptionPackItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SubscriptionScreenViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _subscriptionPackState = MutableStateFlow<NetworkResponse<List<SubscriptionPackItem>>>(NetworkResponse.Initial)
    val subscriptionPackState = _subscriptionPackState.asStateFlow()

    fun getSubscriptionPacks() = viewModelScope.launch {
        _subscriptionPackState.value = NetworkResponse.Loading
        repository.getSubscriptionPacks().onEach {
            _subscriptionPackState.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.map { subscription -> subscription.toSubscriptionPackItem() })
            }
        }.launchIn(viewModelScope)
    }
}