package com.theberdakh.fitness.core.viewmodel

import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CoreViewModel {
    val module = module {
        viewModel { AuthViewModel(repository = get()) }
    }
}