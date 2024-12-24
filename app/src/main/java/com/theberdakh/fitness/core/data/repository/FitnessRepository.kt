package com.theberdakh.fitness.core.data.repository

import com.theberdakh.fitness.core.data.network.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    suspend fun sendCode(): Flow<NetworkResponse<String>>
    suspend fun login(): Flow<NetworkResponse<String>>
    suspend fun logout(): Flow<NetworkResponse<String>>
}