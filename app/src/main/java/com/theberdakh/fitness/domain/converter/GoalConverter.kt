package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.domain.model.Goal
import com.theberdakh.fitness.feature.auth.adapter.GoalPoster

fun NetworkTarget.toDomain() = Goal(name = this.name)
fun Goal.toGoalPoster() = GoalPoster(name = this.name)
fun List<Goal>.toGoalPosters() = this.map { it.toGoalPoster() }