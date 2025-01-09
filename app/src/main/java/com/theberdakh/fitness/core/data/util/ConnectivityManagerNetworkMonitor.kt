package com.theberdakh.fitness.core.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Build.VERSION
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class ConnectivityManagerNetworkMonitor(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }

        val callback = object : NetworkCallback() {
            private val networks = mutableSetOf<Network>()
            override fun onAvailable(network: Network) {
                networks += network
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(networks.isNotEmpty())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        channel.trySend(connectivityManager.isCurrentlyConnected())
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
        .flowOn(ioDispatcher)
        .conflate()
}

/**
 * This version check is useless because midSdk version on this app is already higher. However, this can be used on other apps easily..
 * */
@SuppressLint("ObsoleteSdkInt")
private fun ConnectivityManager.isCurrentlyConnected() = when {
    VERSION.SDK_INT >= Build.VERSION_CODES.M ->activeNetwork?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    else -> activeNetworkInfo?.isConnected
} ?: false
