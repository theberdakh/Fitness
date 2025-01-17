package com.theberdakh.fitness.feature

import com.theberdakh.fitness.MainActivityViewModel
import com.theberdakh.fitness.feature.auth.viewmodel.AuthViewModel
import com.theberdakh.fitness.feature.free_lessons.FreeLessonsViewModel
import com.theberdakh.fitness.feature.home.HomeViewModel
import com.theberdakh.fitness.feature.lesson.LessonScreenViewModel
import com.theberdakh.fitness.feature.lessons.LessonsScreenViewModel
import com.theberdakh.fitness.feature.modules.ModulesScreenViewModel
import com.theberdakh.fitness.feature.notification.NotificationViewModel
import com.theberdakh.fitness.feature.packs.PacksScreenViewModel
import com.theberdakh.fitness.feature.subscriptions.SubscriptionScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CoreViewModel {
    val module = module {
        viewModel { AuthViewModel(repository = get()) }
        viewModel { MainActivityViewModel(preferences = get()) }
        viewModel { SubscriptionScreenViewModel(repository = get()) }
        viewModel { HomeViewModel(repository = get()) }
        viewModel {FreeLessonsViewModel(repository = get())}
        viewModel { LessonScreenViewModel(repository = get()) }
        viewModel { PacksScreenViewModel(repository = get()) }
        viewModel { ModulesScreenViewModel(repository = get()) }
        viewModel { LessonsScreenViewModel(repository = get()) }
        viewModel { NotificationViewModel(repository = get()) }
    }
}