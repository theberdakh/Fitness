package com.theberdakh.fitness.data.preferences

interface PreferenceManager {
    fun saveUserData(localUserPreference: LocalUserPreference)
    fun getUserData(): LocalUserPreference
    fun saveUserSession(localUserSession: LocalUserSession)
    fun getUserSession(): LocalUserSession
    fun clearUserData()
}