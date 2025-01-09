package com.theberdakh.fitness.feature.free_lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.domain.converter.toFreeLessonItem
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FreeLessonsViewModel(private val repository: FitnessRepository) : ViewModel() {

    val getAllFreeLessonsUiState = freeLessonsState(repository).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FreeLessonsUiState.Loading
    )
}

fun freeLessonsState(repository: FitnessRepository, perPage: Int = 10, cursor: String? = null) =
    flow {
        emit(FreeLessonsUiState.Loading)
        when (val result = repository.getFreeLessons(perPage, cursor)) {
            is NetworkResult.Error -> emit(FreeLessonsUiState.Error)
            is NetworkResult.Success -> emit(FreeLessonsUiState.Success(result.data.map { it.toFreeLessonItem() }))
        }
    }


sealed interface FreeLessonsUiState {
    data class Success(val data: List<FreeLessonItem>) : FreeLessonsUiState
    data object Error : FreeLessonsUiState
    data object Loading : FreeLessonsUiState
}