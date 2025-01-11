package com.theberdakh.fitness.domain.model


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