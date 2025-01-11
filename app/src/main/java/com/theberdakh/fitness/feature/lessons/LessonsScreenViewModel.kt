package com.theberdakh.fitness.feature.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.converter.toLessonsModelLesson
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class LessonsScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    fun getLessons(moduleId: Int, isAvailable: Boolean) =
        getLessonsUiState(repository, moduleId, isAvailable).stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
            initialValue = LessonsUiState.Loading
        )

}

private fun getLessonsUiState(repository: FitnessRepository, moduleId: Int, available: Boolean) =
    flow {
        when (val result = repository.getLessons(moduleId)) {
            is Result.Error -> emit(LessonsUiState.Error)
            Result.Loading -> emit(LessonsUiState.Loading)
            is Result.Success -> emit(LessonsUiState.Success(result.data.map { it.toDomain() }.toLessonsModelLesson(isAvailable = available)))
        }
    }

sealed interface LessonsUiState {
    data class Success(val data: List<LessonsModel.Lesson>) : LessonsUiState
    data object Loading : LessonsUiState
    data object Error : LessonsUiState
}