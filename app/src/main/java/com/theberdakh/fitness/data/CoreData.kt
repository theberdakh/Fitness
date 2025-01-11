package com.theberdakh.fitness.data

import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.retrofit.RetrofitFitnessNetwork
import com.theberdakh.fitness.data.util.ConnectivityManagerNetworkMonitor
import com.theberdakh.fitness.data.util.NetworkMonitor
import com.theberdakh.fitness.domain.FitnessRepository
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