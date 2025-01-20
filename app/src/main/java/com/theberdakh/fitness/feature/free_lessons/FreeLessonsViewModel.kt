package com.theberdakh.fitness.feature.free_lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toFreeLessonItem
import com.theberdakh.fitness.domain.converter.toFreeLessonItems
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class FreeLessonsViewModel(repository: FitnessRepository) : ViewModel() {
    private val refresh = MutableStateFlow(0)
    fun refresh() {
        refresh.value++
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val getAllFreeLessonsUiState = refresh.flatMapLatest {
        freeLessonsState(repository)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FreeLessonsUiState.Loading
    )


    fun getFreeLessonsPaginated(repository: FitnessRepository) = flow {
        val flow = repository.getFreeLessonsPaging(10, "")
        flow.collect{
            emit(FreeLessonsPagingState.Success(it.map { lesson -> lesson.toFreeLessonItem() }))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FreeLessonsPagingState.Loading
    )
}



sealed interface FreeLessonsPagingState {
    data class Success(val data: PagingData<FreeLessonItem>) : FreeLessonsPagingState
    data class Error(val message: String) : FreeLessonsPagingState
    data object Loading : FreeLessonsPagingState
}

fun freeLessonsState(repository: FitnessRepository, perPage: Int = 10, cursor: String? = null) =
    flow {
        emit(FreeLessonsUiState.Loading)
        when (val result = repository.getFreeLessons(perPage, cursor)) {
            is Result.Error -> {
                emit(FreeLessonsUiState.Error(result.message))
            }

            is Result.Success -> {
                emit(FreeLessonsUiState.Success(result.data.toFreeLessonItems()))
            }
        }
    }


sealed interface FreeLessonsUiState {
    data class Success(val data: List<FreeLessonItem>) : FreeLessonsUiState
    data class Error(val message: String) : FreeLessonsUiState
    data object Loading : FreeLessonsUiState
}