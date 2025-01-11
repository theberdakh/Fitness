package com.theberdakh.fitness.feature.free_lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.converter.toFreeLessonItems
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

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
            is Result.Error -> emit(FreeLessonsUiState.Error)
            Result.Loading -> emit(FreeLessonsUiState.Loading)
            is Result.Success -> emit(FreeLessonsUiState.Success(result.data.toFreeLessonItems()))
        }
    }


sealed interface FreeLessonsUiState {
    data class Success(val data: List<FreeLessonItem>) : FreeLessonsUiState
    data object Error : FreeLessonsUiState
    data object Loading : FreeLessonsUiState
}