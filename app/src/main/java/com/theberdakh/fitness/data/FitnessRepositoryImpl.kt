package com.theberdakh.fitness.data

import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.data.preferences.FitnessPreferences
import com.theberdakh.fitness.data.preferences.LocalUserSession
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.model.Goal
import com.theberdakh.fitness.domain.model.Lesson
import com.theberdakh.fitness.domain.model.Module
import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionPack
import com.theberdakh.fitness.domain.model.UserPreference
import com.theberdakh.fitness.domain.model.toDomain

class FitnessRepositoryImpl(
    private val networkDataSource: FitnessNetworkDataSource,
    private val preferences: FitnessPreferences
) : FitnessRepository {

    override suspend fun sendCode(request: NetworkSendCodeRequest): Result<String> {
        networkDataSource.sendCode(request).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.message)
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun login(request: NetworkLoginRequest): Result<UserPreference> {
        networkDataSource.login(request).let {
            return when (it) {
                is NetworkResult.Success -> {
                    preferences.saveUserSession(
                        LocalUserSession(
                            accessToken = it.data.accessToken,
                            tokenType = it.data.tokenType,
                            isLoggedIn = true
                        )
                    )
                    Result.Success(it.data.toDomain())
                }

                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun logout(): Result<String> {
        networkDataSource.logout().let {
            return when (it) {
                is NetworkResult.Success -> {
                    preferences.clear()
                    Result.Success(it.data)
                }

                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getGoals(): Result<List<Goal>> {
        networkDataSource.getTargets().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { target -> target.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getProfile(): Result<UserPreference> {
        networkDataSource.getProfile().let {
            return when (it) {
                is NetworkResult.Success -> {
                    preferences.saveUserData(
                        preferences.getUserData().copy(
                            name = it.data.name,
                            phone = it.data.phone,
                            userGoalId = it.data.targetId ?: UserPreference.NO_GOAL_ID
                        )
                    )
                    Result.Success(preferences.getUserData().toDomain())
                }

                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun updateName(request: NetworkUpdateNameRequest): Result<UserPreference> {
        networkDataSource.updateName(request).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.toDomain())
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getSubscriptionPacks(): Result<List<SubscriptionPack>> {
        networkDataSource.getSubscriptionPacks().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { pack -> pack.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getModules(packId: Int): Result<List<Module>> {
        networkDataSource.getModules(packId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { module -> module.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getModulesByOrderId(orderId: Int): Result<List<Module>> {
        networkDataSource.getModulesByOrderId(orderId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { module -> module.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getLessons(moduleId: Int): Result<List<Lesson>> {
        networkDataSource.getLessons(moduleId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { lesson -> lesson.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getRandomFreeLessons(): Result<List<Lesson>> {
        networkDataSource.getRandomFreeLessons().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { lesson -> lesson.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getFreeLessons(
        perPage: Int,
        cursor: String?
    ): Result<List<Lesson>> {

        networkDataSource.getFreeLessons(perPage, cursor).let {
            return when (it) {
                is NetworkResult.Success -> {
                    Result.Success(it.data.map { lesson -> lesson.toDomain() })
                }
                is NetworkResult.Error -> {
                    Result.Error(it.message)
                }
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

    override suspend fun getMyOrders(): Result<List<SubscriptionOrder>> {
        networkDataSource.getMyOrders().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { order -> order.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

}