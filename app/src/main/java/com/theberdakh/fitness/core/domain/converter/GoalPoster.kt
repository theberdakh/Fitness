package com.theberdakh.fitness.core.domain.converter

import com.theberdakh.fitness.core.data.source.network.model.mobile.NetworkTarget
import com.theberdakh.fitness.feature.auth.model.GoalPoster

fun NetworkTarget.toDomain() = GoalPoster(this.name)