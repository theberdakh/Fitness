package com.theberdakh.fitness.app

import android.app.Application
import com.theberdakh.fitness.core.data.CoreData
import com.theberdakh.fitness.core.network.CoreNetwork
import com.theberdakh.fitness.core.preferences.CorePrefs
import com.theberdakh.fitness.core.viewmodel.CoreViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FitnessApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FitnessApp)
            modules(listOf(CorePrefs.module, CoreNetwork.module, CoreData.module, CoreViewModel.module))
        }
    }
}