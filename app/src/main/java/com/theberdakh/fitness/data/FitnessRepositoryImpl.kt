package com.theberdakh.fitness.data

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.theberdakh.fitness.data.network.FitnessNetworkDataSource
import com.theberdakh.fitness.data.network.NetworkResult
import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.data.network.model.mobile.toDomain
import com.theberdakh.fitness.data.preferences.FitnessPreferences
import com.theberdakh.fitness.data.preferences.LocalUserSession
import com.theberdakh.fitness.domain.FitnessRepository
import com.theberdakh.fitness.domain.Result
import com.theberdakh.fitness.domain.converter.toDomain
import com.theberdakh.fitness.domain.model.Goal
import com.theberdakh.fitness.domain.model.Lesson
import com.theberdakh.fitness.domain.model.Message
import com.theberdakh.fitness.domain.model.Module
import com.theberdakh.fitness.domain.model.Notification
import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionPack
import com.theberdakh.fitness.domain.model.UserPreference
import com.theberdakh.fitness.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
                    Log.i("LoginUiState Success", "login: ${it.data}")
                    preferences.saveUserSession(
                        LocalUserSession(
                            accessToken = it.data.accessToken,
                            tokenType = it.data.tokenType,
                            isLoggedIn = true
                        )
                    )
                    Log.i("LoginUiState", preferences.getUserSession().toString())
                    Result.Success(it.data.toDomain())
                }

                is NetworkResult.Error -> {
                    Log.i("LoginUiState", "login: ${it.message}")
                    Result.Error(it.message)
                }
            }
        }
    }

    override suspend fun logout(): Result<String> {
        networkDataSource.logout().let {
            return when (it) {
                is NetworkResult.Success -> {
                    preferences.clear()
                    Result.Success(it.data.message)
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
        return when (val networkResult = networkDataSource.getProfile()) {
            is NetworkResult.Error -> {
                Result.Success(preferences.getUserData().toDomain())
            }

            is NetworkResult.Success -> {
                val updatedUser = preferences.getUserData().copy(
                    name = networkResult.data.name,
                    phone = networkResult.data.phone,
                    userGoalId = networkResult.data.targetId ?: UserPreference.NO_GOAL_ID
                )
                preferences.saveUserData(updatedUser)
                Result.Success(preferences.getUserData().toDomain())
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
                is NetworkResult.Error -> {
                    if (it.message == "Unauthorized") {
                        preferences.isUserLoggedIn = false
                    }
                    Result.Error(it.message)
                }
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

    override suspend fun getAllOrders(): Result<List<SubscriptionOrder>> {
        networkDataSource.getAllOrders().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { order -> order.toDomain() })
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

    override suspend fun getNotifications(): Result<List<Notification>> {
        networkDataSource.getNotifications().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { networkNotification -> networkNotification.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getNotification(notificationId: Int): Result<Notification> {
        networkDataSource.getNotification(notificationId).let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.toDomain())
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getMessages(): Result<List<Message>> {
        networkDataSource.getMessages().let {
            return when (it) {
                is NetworkResult.Success -> Result.Success(it.data.map { networkMessage -> networkMessage.toDomain() })
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun sendMessage(message: String): Result<Message> {
        networkDataSource.sendMessage(message).let {
            return when (it) {
                is NetworkResult.Success -> {
                    Log.i("Chat Message", "sendMessage: ${it.data}")
                    Result.Success(it.data.toDomain())
                }
                is NetworkResult.Error -> Result.Error(it.message)
            }
        }
    }

    override suspend fun getFreeLessonsPaging(
        perPage: Int,
        cursor: String?
    ): Flow<PagingData<Lesson>> {
        return networkDataSource.getFreeLessonsPaging(perPage, cursor).map { pagingData ->
            pagingData.map { lesson -> lesson.toDomain() }
        }
    }

}