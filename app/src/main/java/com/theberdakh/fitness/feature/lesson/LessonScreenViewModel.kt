package com.theberdakh.fitness.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.NetworkResult
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class LessonScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    fun getLessonUiState(lessonId: Int) = lessonState(repository, lessonId).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = LessonUiState.Loading
    )
}

private fun lessonState(repository: FitnessRepository, lessonId: Int) = flow {
    when (val result = repository.getLesson(lessonId)) {
        is NetworkResult.Error -> emit(LessonUiState.Error)
        is NetworkResult.Success -> emit(LessonUiState.Success(result.data))
    }
}

sealed interface LessonUiState {
    data class Success(val data: NetworkLesson) : LessonUiState
    data object Loading : LessonUiState
    data object Error : LessonUiState
}
