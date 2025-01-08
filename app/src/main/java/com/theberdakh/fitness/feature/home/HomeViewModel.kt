package com.theberdakh.fitness.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.core.domain.converter.toVideoItem
import com.theberdakh.fitness.feature.home.model.ListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _freeLessons = MutableStateFlow<NetworkResponse<List<ListItem.VideoItem>>>(NetworkResponse.Initial)
    val freeLessons = _freeLessons.asStateFlow()
    fun getRandomFreeLessons() = viewModelScope.launch {
        _freeLessons.value = NetworkResponse.Loading
        repository.getRandomFreeLessons().onEach {
            _freeLessons.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.map { lesson -> lesson.toVideoItem() })
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading -> NetworkResponse.Loading
            }
        }.launchIn(viewModelScope)
    }


}