package com.theberdakh.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(true);
    val isLoading = _isLoading.asStateFlow();
    val isLoggedIn = false

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }
}