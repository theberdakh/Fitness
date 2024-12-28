package com.theberdakh.fitness.feature.common

import com.theberdakh.fitness.feature.common.error.ErrorDelegateImpl
import com.theberdakh.fitness.feature.common.network.NetworkStateManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object Common {
    val module = module {
        single { ErrorDelegateImpl(androidContext()) }
        single { NetworkStateManager(androidContext(), errorDelegateImpl = get()) }
    }
}