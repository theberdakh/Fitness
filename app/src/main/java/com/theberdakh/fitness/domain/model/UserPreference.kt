package com.theberdakh.fitness.domain.model

import com.theberdakh.fitness.data.network.model.mobile.NetworkProfile


fun NetworkProfile.toDomain() = UserPreference(
    id = this.id ?: UserPreference.NO_ID,
    name = this.name,
    phone = this.phone,
    goalId = this.targetId ?: UserPreference.NO_GOAL_ID,
)

data class UserPreference(
    val id: Int,
    val name: String,
    val phone: String,
    val goalId: Int,
) {
    companion object {
        const val NO_GOAL_ID = -1
        const val NO_ID = -1
    }
}