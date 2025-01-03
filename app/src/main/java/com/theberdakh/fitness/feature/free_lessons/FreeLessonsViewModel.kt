package com.theberdakh.fitness.feature.free_lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.data.NetworkFitnessRepository
import com.theberdakh.fitness.core.network.model.NetworkResponse
import com.theberdakh.fitness.core.network.model.mobile.toFreeLessonItem
import com.theberdakh.fitness.feature.free_lessons.model.FreeLessonItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FreeLessonsViewModel(private val repository: NetworkFitnessRepository): ViewModel() {

    private val _freeVideosState = MutableStateFlow<NetworkResponse<List<FreeLessonItem>>>(NetworkResponse.Initial)
    val freeVideosState = _freeVideosState.asStateFlow()

    fun getAllFreeLessons(perPage: Int = 10, cursor: String? = null) = viewModelScope.launch {
       _freeVideosState.value = NetworkResponse.Loading
        repository.getFreeLessons(perPage, cursor).onEach {
            _freeVideosState.value = when(it){
                is NetworkResponse.Error -> NetworkResponse.Error(it.message)
                NetworkResponse.Initial -> NetworkResponse.Initial
                NetworkResponse.Loading -> NetworkResponse.Loading
                is NetworkResponse.Success -> NetworkResponse.Success(it.data.data.map { lesson -> lesson.toFreeLessonItem() })
            }
       }.launchIn(viewModelScope)
    }

}