package com.theberdakh.fitness.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.feature.chat.model.MessageItem
import com.theberdakh.fitness.feature.chat.model.toMessageItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ChatWithCoachScreenViewModel(private val repository: FitnessRepository) : ViewModel() {

    val messages = getChatUiState(repository).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatUiState.Loading
    )


}


private fun getChatUiState(repository: FitnessRepository) = flow {
    when (val result = repository.getMessages()) {
        is Result.Error -> emit(ChatUiState.Error(result.message))
        is Result.Success -> emit(ChatUiState.Success(result.data.map { it.toMessageItem() }))
    }
}

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class Success(val messages: List<MessageItem>) : ChatUiState
    data class Error(val message: String) : ChatUiState
}