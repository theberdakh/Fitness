package com.theberdakh.fitness.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.domain.FitnessRepository
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
        is Result.Error -> emit(LessonUiState.Error)
        Result.Loading -> emit(LessonUiState.Loading)
        is Result.Success -> emit(LessonUiState.Success(result.data))
    }
}

sealed interface LessonUiState {
    data class Success(val data: NetworkLesson) : LessonUiState
    data object Loading : LessonUiState
    data object Error : LessonUiState
}
