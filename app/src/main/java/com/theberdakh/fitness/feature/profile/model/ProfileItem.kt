package com.theberdakh.fitness.feature.profile.model

import androidx.annotation.ColorRes

data class ProfileItem(
    val title: String,
    val type: ProfileItemType,
    @ColorRes val textColor: Int
)

enum class ProfileItemType {
    SUBSCRIPTIONS,
    ABOUT_US,
    LOGOUT
}


