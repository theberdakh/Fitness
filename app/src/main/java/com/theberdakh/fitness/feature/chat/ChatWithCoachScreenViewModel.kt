package com.theberdakh.fitness.feature.chat

import android.util.Log
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

    fun sendMessage(message: String) = getSendMessageUiState(repository, message).stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
        initialValue = SendMessageUiState.Loading
    )




}

private fun getSendMessageUiState(repository: FitnessRepository, message: String) = flow {
    when (val result = repository.sendMessage(message)) {
        is Result.Error -> emit(SendMessageUiState.Error(result.message))
        is Result.Success -> emit(SendMessageUiState.Success(result.data.toMessageItem()))
    }
}

sealed interface SendMessageUiState {
    data object Loading : SendMessageUiState
    data class Success(val message: MessageItem) : SendMessageUiState
    data class Error(val message: String) : SendMessageUiState
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