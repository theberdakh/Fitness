package com.theberdakh.fitness.feature.packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.feature.packs.model.PackListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PacksScreenViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _subscriptionPacks = MutableStateFlow<NetworkResponse<List<PackListItem>>>(NetworkResponse.Initial)
    val subscriptionPacks = _subscriptionPacks.asStateFlow()
    fun getSubscribedPacks() = viewModelScope.launch {
        _subscriptionPacks.value = NetworkResponse.Loading
        repository.getMyOrders().onEach {
            _subscriptionPacks.value = when(it){
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.map { pack -> PackListItem.PackItemUnsubscribed(
                    packId = pack.pack.id,
                    title = pack.pack.title,
                    amount = pack.amount,
                    statusEnum = PackListItem.PackItemUnsubscribed.statusEnum(pack.status),
                    createdAt = pack.createdAt,
                    orderId = pack.id
                ) })
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                is NetworkResponse.Loading -> NetworkResponse.Loading
                else -> NetworkResponse.Initial
            }
        }.launchIn(viewModelScope)
    }
}