package com.theberdakh.fitness.domain.model

data class Message(
    val id: Int,
    val text: String,
    val isMyMessage: Boolean,
    val time: String
)
