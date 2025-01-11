package com.theberdakh.fitness.data.preferences

data class LocalUserSession(
    val accessToken: String,
    val tokenType: String,
    val isLoggedIn: Boolean = false
)
