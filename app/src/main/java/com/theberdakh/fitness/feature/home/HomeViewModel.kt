package com.theberdakh.fitness.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toVideoItems
import com.theberdakh.fitness.feature.home.model.ListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: FitnessRepository) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeUiState: StateFlow<HomeUiState> = refreshTrigger.flatMapLatest {
        homeUiState(repository)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    fun refresh() {
        refreshTrigger.value++
    }
}

private fun homeUiState(repository: FitnessRepository): Flow<HomeUiState> = flow {
    emit(HomeUiState.Loading)
    when (val result = repository.getRandomFreeLessons()) {
        is Result.Error -> emit(HomeUiState.Error(result.message))
        is Result.Success -> emit(HomeUiState.Success(result.data.toVideoItems()))
    }
}

sealed interface HomeUiState {
    data class Success(val data: List<ListItem.VideoItem>) : HomeUiState
    data class Error(val message: String) : HomeUiState
    data object Loading : HomeUiState
}