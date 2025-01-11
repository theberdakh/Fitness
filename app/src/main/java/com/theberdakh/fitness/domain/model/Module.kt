package com.theberdakh.fitness.domain.model

data class Module(
    val id: Int,
    val name: String,
    val packId: Int,
    val isAvailable: Boolean,
    val lessons: List<Lesson>,
    val isViewFinished: Boolean,
)