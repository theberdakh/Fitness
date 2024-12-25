package com.theberdakh.fitness.core.network.model.mobile

import com.theberdakh.fitness.feature.auth.model.GoalPoster

data class Target(
    val id: Int,
    val name: String
)

fun Target.toDomain() = GoalPoster(this.name)
