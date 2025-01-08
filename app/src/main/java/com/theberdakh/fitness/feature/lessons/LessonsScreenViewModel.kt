package com.theberdakh.fitness.feature.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.data.source.network.model.NetworkResponse
import com.theberdakh.fitness.feature.lessons.adapter.LessonsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LessonsScreenViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _lessons = MutableStateFlow<NetworkResponse<List<LessonsModel.Lesson>>>(NetworkResponse.Initial)
    val lessons = _lessons.asStateFlow()
    fun getLessons(moduleId: Int, isAvailable: Boolean = false) = viewModelScope.launch {
        repository.getLessons(moduleId).onEach {
            _lessons.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.map { lesson -> LessonsModel.Lesson(lesson.id, lesson.title, lesson.youtubeUrl, lesson.isFree ?: false, isAvailable = isAvailable) })
            }
        }.launchIn(viewModelScope)
    }

}