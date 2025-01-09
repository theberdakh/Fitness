package com.theberdakh.fitness.feature.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            is NetworkResult.Error -> emit(LessonsUiState.Error)
            is NetworkResult.Success -> emit(LessonsUiState.Success(result.data.map { lesson ->
                LessonsModel.Lesson(
                    lesson.id,
                    lesson.title,
                    lesson.youtubeUrl,
                    lesson.isFree ?:false,
                    isAvailable = available
                )
            }))
        }
    }

sealed interface LessonsUiState {
    data class Success(val data: List<LessonsModel.Lesson>) : LessonsUiState
    data object Loading : LessonsUiState
    data object Error : LessonsUiState
}