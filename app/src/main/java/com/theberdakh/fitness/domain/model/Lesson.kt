package com.theberdakh.fitness.domain.model

data class Lesson(
    val id: Int,
    val name: String,
    val module: Int,
    val url: String,
    val isFree: Boolean
)
