package com.theberdakh.fitness.data.preferences

import android.content.SharedPreferences

class FitnessPreferences(private val preferences: SharedPreferences): PreferenceManager {
    fun  clear(): Boolean {
        preferences.edit().clear().apply()
        return true
    }

    var isUserLoggedIn by BooleanPreference(preferences)
    private var accessToken by StringPreference(preferences)
    private var tokenType by StringPreference(preferences)
    private var userId by IntPreference(preferences)
    private var userName by StringPreference(preferences)
    private var userPhone by StringPreference(preferences)
    private var userTargetId by IntPreference(preferences)

    override fun getUserData(): LocalUserPreference {
        return LocalUserPreference(
            id = userId,
            name = userName,
            phone = userPhone,
            userGoalId = userTargetId
        )
    }

    override fun saveUserSession(localUserSession: LocalUserSession) {
        isUserLoggedIn = localUserSession.isLoggedIn
        tokenType = localUserSession.tokenType
        accessToken = localUserSession.accessToken
    }

    override fun getUserSession(): LocalUserSession {
        return LocalUserSession(
            isLoggedIn = isUserLoggedIn,
            tokenType = tokenType,
            accessToken = accessToken
        )
    }


    override fun saveUserData(localUserPreference: LocalUserPreference) {
        localUserPreference.id.let { userId = it }
        localUserPreference.name.let { userName = it }
        localUserPreference.phone.let { userPhone = it }
        localUserPreference.userGoalId.let { userTargetId = it }
    }

    override fun clearUserData() {

    }

}