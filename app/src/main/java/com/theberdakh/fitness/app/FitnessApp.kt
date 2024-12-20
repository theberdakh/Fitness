package com.theberdakh.fitness.app

import android.app.Application
import com.theberdakh.fitness.core.preferences.CorePrefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FitnessApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FitnessApp)
            modules(listOf(CorePrefs.module))
        }
    }
}