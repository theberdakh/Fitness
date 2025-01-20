package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.auth.NetworkLoginResponse
import com.theberdakh.fitness.data.preferences.LocalUserPreference
import com.theberdakh.fitness.data.preferences.LocalUserSession
import com.theberdakh.fitness.domain.model.UserPreference



fun NetworkLoginResponse.toDomain() = UserPreference(
    id = this.user.id,
    name = this.user.name ?: "",
    phone = this.user.phone,
    goalId = this.user.targetId ?: UserPreference.NO_GOAL_ID,
)


fun UserPreference.toLocalUserPreference() = LocalUserPreference(
    id = id,
    name = name,
    phone = phone,
    userGoalId = goalId,
)

fun LocalUserPreference.toDomain() = UserPreference(
    id = id,
    name = name,
    phone = phone,
    goalId = userGoalId,
)