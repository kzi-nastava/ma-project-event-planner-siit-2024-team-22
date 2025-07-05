package com.example.eventplannerteam22.session

interface SessionRepository {
    fun setAccessToken(accessToken: String): SessionRepository
    fun getAccessToken(): String

    fun setRefreshToken(refreshToken: String): SessionRepository
    fun getRefreshToken(): String

    fun setExpiresIn(expiresIn: Long): SessionRepository
    fun getExpiresIn(): Long

    fun setLoggedIn(): SessionRepository
    fun getLoggedIn(): Boolean

    fun clearSession()
}