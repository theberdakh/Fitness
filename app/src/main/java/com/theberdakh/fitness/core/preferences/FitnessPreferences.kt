package com.theberdakh.fitness.core.preferences

import android.content.SharedPreferences

class FitnessPreferences(private val preferences: SharedPreferences){
    var isUserLoggedIn by BooleanPreference(preferences)
}