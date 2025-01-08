package com.theberdakh.fitness.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LessonScreenViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _lesson = MutableStateFlow<NetworkResponse<NetworkLesson>>(NetworkResponse.Initial)
    val lesson = _lesson.asStateFlow()
    fun getLesson(lessonId: Int) = viewModelScope.launch {
        _lesson.value = NetworkResponse.Loading
        repository.getLesson(lessonId).onEach {
            _lesson.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> NetworkResponse.Success(it.data)
            }
        }.launchIn(viewModelScope)
    }
}