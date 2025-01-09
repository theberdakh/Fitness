package com.theberdakh.fitness.core.data

import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.FitnessRepositoryImpl
import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.retrofit.RetrofitFitnessNetwork
import org.koin.dsl.module

object CoreData {
    val module = module {
        single<FitnessNetworkDataSource>{ RetrofitFitnessNetwork(get()) }
        single<FitnessRepository> { FitnessRepositoryImpl(get()) }
    }
}