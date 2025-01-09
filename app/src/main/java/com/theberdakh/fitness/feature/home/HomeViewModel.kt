package com.theberdakh.fitness.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.domain.converter.toVideoItem
import com.theberdakh.fitness.feature.home.model.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: FitnessRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = homeUiState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
}

private fun homeUiState(repository: FitnessRepository): Flow<HomeUiState> = flow {
    emit(HomeUiState.Loading)
    when (val result = repository.getRandomFreeLessons()) {
        is NetworkResult.Error -> emit(HomeUiState.Error)
        is NetworkResult.Success -> emit(HomeUiState.Success(result.data.map { it.toVideoItem() }))
    }
}

sealed interface HomeUiState {
    data class Success(val data: List<ListItem.VideoItem>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}