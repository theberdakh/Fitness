package com.theberdakh.fitness.domain

import com.theberdakh.fitness.data.network.model.auth.NetworkLoginRequest
import com.theberdakh.fitness.data.network.model.auth.NetworkSendCodeRequest
import com.theberdakh.fitness.data.network.model.mobile.NetworkLesson
import com.theberdakh.fitness.data.network.model.mobile.NetworkUpdateNameRequest
import com.theberdakh.fitness.domain.model.Goal
import com.theberdakh.fitness.domain.model.Lesson
import com.theberdakh.fitness.domain.model.Message
import com.theberdakh.fitness.domain.model.Module
import com.theberdakh.fitness.domain.model.Notification
import com.theberdakh.fitness.domain.model.SubscriptionOrder
import com.theberdakh.fitness.domain.model.SubscriptionPack
import com.theberdakh.fitness.domain.model.UserPreference

interface FitnessRepository {

    suspend fun sendCode(request: NetworkSendCodeRequest): Result<String>

    suspend fun login(request: NetworkLoginRequest): Result<UserPreference>

    suspend fun logout(): Result<String>

    suspend fun getGoals(): Result<List<Goal>>

    suspend fun getProfile(): Result<UserPreference>

    suspend fun updateName(request: NetworkUpdateNameRequest): Result<UserPreference>

    suspend fun getSubscriptionPacks(): Result<List<SubscriptionPack>>

    suspend fun getModules(packId: Int): Result<List<Module>>

    suspend fun getModulesByOrderId(orderId: Int): Result<List<Module>>

    suspend fun getLessons(moduleId: Int): Result<List<Lesson>>

    suspend fun getRandomFreeLessons(): Result<List<Lesson>>

    suspend fun getFreeLessons(perPage: Int, cursor: String?): Result<List<Lesson>>

    suspend fun getLesson(lessonId: Int): Result<NetworkLesson>

    suspend fun getMyOrders(): Result<List<SubscriptionOrder>>

    suspend fun getNotifications(): Result<List<Notification>>

    suspend fun getNotification(notificationId: Int): Result<Notification>

    suspend fun getMessages(): Result<List<Message>>
}