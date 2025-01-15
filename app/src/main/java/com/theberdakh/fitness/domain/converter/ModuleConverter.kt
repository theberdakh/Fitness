package com.theberdakh.fitness.domain.converter

import com.theberdakh.fitness.data.network.model.mobile.NetworkModule
import com.theberdakh.fitness.data.network.model.mobile.NetworkOrderModule
import com.theberdakh.fitness.domain.model.Module
import com.theberdakh.fitness.feature.modules.adapter.model.ModulesModel

fun NetworkOrderModule.toDomain() = Module(
    id = id,
    name = title,
    packId = packId,
    lessons = lessons.map { it.toDomain() },
    isViewFinished = isViewFinished,
    isAvailable = isAvailable
)

fun NetworkModule.toDomain() = Module(
    id = id,
    name = title,
    packId = packId,
    isAvailable = false,
    lessons = emptyList(),
    isViewFinished = false
)

fun Module.toExtendedModuleItem() = ModulesModel.ModuleItemExtended(
    moduleId = id,
    title = name,
    isAvailable = isAvailable,
    totalLessons = lessons.size
)
fun List<Module>.toExtendedModuleItems() = this.map { it.toExtendedModuleItem() }

fun Module.toModuleItem() = ModulesModel.ModuleItem(
    moduleId = id,
    title = name,
)
fun List<Module>.toModuleItems() = this.map { it.toModuleItem() }
