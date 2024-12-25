package com.theberdakh.fitness.core.data

import com.theberdakh.fitness.SplashScreenViewModel
import org.koin.dsl.module

object CoreData {
    val module = module {
        single { NetworkFitnessRepository(get()) }
    }
}