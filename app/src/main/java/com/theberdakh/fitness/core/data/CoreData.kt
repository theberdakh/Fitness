package com.theberdakh.fitness.core.data

import com.theberdakh.fitness.core.data.source.FitnessRepository
import com.theberdakh.fitness.core.data.source.FitnessRepositoryImpl
import com.theberdakh.fitness.core.data.source.network.FitnessNetworkDataSource
import com.theberdakh.fitness.core.data.source.network.retrofit.RetrofitFitnessNetwork
import com.theberdakh.fitness.core.data.util.ConnectivityManagerNetworkMonitor
import com.theberdakh.fitness.core.data.util.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CoreData {
    val module = module {
        single<FitnessNetworkDataSource>{ RetrofitFitnessNetwork(get()) }
        single<FitnessRepository> { FitnessRepositoryImpl(networkDataSource = get()) }
        single<NetworkMonitor> { ConnectivityManagerNetworkMonitor(context = androidContext(), ioDispatcher = Dispatchers.IO) }
    }
}