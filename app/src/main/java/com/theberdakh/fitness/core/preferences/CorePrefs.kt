package com.theberdakh.fitness.core.preferences

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object CorePrefs {
    private const val SHARED_PREFERENCE_NAME = "FitnessAppPreferences"

    val module = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
        single { FitnessPreferences(preferences = get()) }
    }

}