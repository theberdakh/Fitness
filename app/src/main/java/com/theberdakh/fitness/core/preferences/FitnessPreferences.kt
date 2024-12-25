package com.theberdakh.fitness.core.preferences

import android.content.SharedPreferences
import com.theberdakh.fitness.core.network.model.auth.LoginResponse

class FitnessPreferences(private val preferences: SharedPreferences){
    var isUserLoggedIn by BooleanPreference(preferences)
    var accessToken by StringPreference(preferences)
    var tokenType by StringPreference(preferences)
    var userId by IntPreference(preferences)
    var userName by StringPreference(preferences)
    var userPhone by StringPreference(preferences)
    var userTargetId by IntPreference(preferences)

}