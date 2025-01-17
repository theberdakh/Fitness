package com.theberdakh.fitness.app

import android.app.Application
import com.theberdakh.fitness.data.CoreData
import com.theberdakh.fitness.data.network.FitnessNetwork
import com.theberdakh.fitness.data.preferences.CorePrefs
import com.theberdakh.fitness.feature.CoreViewModel
import com.theberdakh.fitness.feature.common.Common
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FitnessApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FitnessApp)
            modules(listOf(CorePrefs.module, FitnessNetwork.module, CoreData.module, CoreViewModel.module, Common.module))
        }
    }
}