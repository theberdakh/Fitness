package com.theberdakh.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.fitness.core.preferences.FitnessPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val preferences: FitnessPreferences): ViewModel() {
    private val _isLoading = MutableStateFlow(true);
    val isLoading = _isLoading.asStateFlow();
    val isLoggedIn = preferences.isUserLoggedIn

    init {
        viewModelScope.launch {
            delay(200)
            _isLoading.value = false
        }
    }
}