package com.theberdakh.fitness.data

import android.util.Log
import com.theberdakh.fitness.core.log.LogEx.TAG
import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.NetworkMessage
import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrder
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkPack
import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile
import com.theberdakh.fitness.data.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result

class FitnessRepositoryImpl(private val networkDataSource: FitnessNetworkDataSource) :
    FitnessRepository {

    override suspend fun sendCode(request: NetworkSendCodeRequest): Result<NetworkMessage> {
        networkDataSource.sendCode(request).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun login(request: NetworkLoginRequest): Result<NetworkLoginResponse> {
        networkDataSource.login(request).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun logout(): Result<String> {
        networkDataSource.logout().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getTargets(): Result<List<NetworkTarget>> {
        networkDataSource.getTargets().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getProfile(): Result<NetworkProfile> {
        networkDataSource.getProfile().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun updateName(request: NetworkUpdateNameRequest): Result<NetworkProfile> {
        networkDataSource.updateName(request).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getSubscriptionPacks(): Result<List<NetworkPack>> {
        networkDataSource.getSubscriptionPacks().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getModules(packId: Int): Result<List<NetworkModule>> {
        networkDataSource.getModules(packId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getModulesByOrderId(orderId: Int): Result<List<NetworkOrderModule>> {
        networkDataSource.getModulesByOrderId(orderId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getLessons(moduleId: Int): Result<List<NetworkLesson>> {
        networkDataSource.getLessons(moduleId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getRandomFreeLessons(): Result<List<NetworkLesson>> {
        networkDataSource.getRandomFreeLessons().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ): Result<List<NetworkLesson>> {
        networkDataSource.getFreeLessons(perPage, cursor).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getLesson(lessonId: Int): Result<NetworkLesson> {
        networkDataSource.getLesson(lessonId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getMyOrders(): Result<List<NetworkOrder>> {
        networkDataSource.getMyOrders().let {
            return when (it) {
                is NetworkResult.Success -> {
                    Result.Success(it.data)
                }
                is NetworkResult.Error -> {
                    Result.Error(it.message)
                }
            }
        }
    }

}