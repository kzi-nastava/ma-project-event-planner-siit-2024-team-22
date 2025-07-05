package com.example.eventplannerteam22.session

import android.content.SharedPreferences
import javax.inject.Inject

class SessionRepositorySharedPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionRepository {
    private val logTag = "SessionRepositorySharedPreferencesImpl"

    override fun setAccessToken(accessToken: String): SessionRepository {
        sharedPreferences
            .edit()
            .putString(SharedPreferencesKey.AccessToken.key, accessToken)
            .apply()
        return this
    }

    override fun getAccessToken(): String {
        return sharedPreferences
            .getString(SharedPreferencesKey.AccessToken.key, "")
            .toString()
    }

    override fun setRefreshToken(refreshToken: String): SessionRepository {
        sharedPreferences
            .edit()
            .putString(SharedPreferencesKey.RefreshToken.key, refreshToken)
            .apply()
        return this
    }

    override fun getRefreshToken(): String {
        return sharedPreferences
            .getString(SharedPreferencesKey.AccessToken.key, "")
            .toString()
    }

    override fun setExpiresIn(expiresIn: Long): SessionRepository {
        sharedPreferences
            .edit()
            .putLong(SharedPreferencesKey.ExpiresIn.key, expiresIn)
            .apply()
        return this
    }

    override fun getExpiresIn(): Long {
        return sharedPreferences
            .getLong(SharedPreferencesKey.ExpiresIn.key, -1)
    }

    override fun setLoggedIn(): SessionRepository {
        sharedPreferences
            .edit()
            .putBoolean(SharedPreferencesKey.LoggedIn.key, true)
            .apply()
        return this
    }

    override fun getLoggedIn(): Boolean {
        return sharedPreferences
            .getBoolean(SharedPreferencesKey.LoggedIn.key, false)
    }

    override fun clearSession() {
        with(sharedPreferences.edit()) {
            for (keyType in SharedPreferencesKey.values) {
                remove(keyType.key)
            }
            apply()
        }
    }
}