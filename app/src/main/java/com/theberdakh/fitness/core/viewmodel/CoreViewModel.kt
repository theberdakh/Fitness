package com.theberdakh.fitness.core.viewmodel

import com.theberdakh.fitness.SplashScreenViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.free_lessons.FreeLessonsViewModel
import com.theberdakh.fitness.feature.home.HomeViewModel
import com.theberdakh.fitness.feature.lesson.LessonScreenViewModel
import com.theberdakh.fitness.feature.subscriptions.SubscriptionScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CoreViewModel {
    val module = module {
        viewModel { AuthViewModel(repository = get(), preferences = get(), networkStateManager = get()) }
        viewModel { SplashScreenViewModel(preferences = get()) }
        viewModel { SubscriptionScreenViewModel(repository = get()) }
        viewModel { HomeViewModel(repository = get()) }
        viewModel {FreeLessonsViewModel(repository = get())}
        viewModel { LessonScreenViewModel(repository = get()) }
    }
}