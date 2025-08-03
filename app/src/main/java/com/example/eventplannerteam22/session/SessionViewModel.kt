package com.example.eventplannerteam22.session

import android.util.Log
import androidx.lifecycle.ViewModel
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.auth.TokenResponse
import com.example.eventplannerteam22.profile.domen.Authority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val logTag = "SessionViewModel"
    private val _session = MutableStateFlow<Session>(Session())
    var session = _session.asStateFlow()

    init {
        loadPersistedSession()
    }

    private fun loadPersistedSession() {
        _session.value = Session(
            accessToken = sessionRepository.getAccessToken(),
            refreshToken = sessionRepository.getRefreshToken(),
            expiresIn = sessionRepository.getExpiresIn(),
            loggedIn = sessionRepository.getLoggedIn(),
            userId = sessionRepository.getUserId().takeIf { it != -1 }
        )
    }

    fun updateState(
        accessToken: String? = null,
        refreshToken: String? = null,
        expiresIn: Long? = null,
        loggedIn: Boolean? = null
    ) {
        _session.update {
            it.copy(
                accessToken = accessToken ?: it.accessToken,
                refreshToken = refreshToken ?: it.refreshToken,
                expiresIn = expiresIn ?: it.expiresIn,
                loggedIn = loggedIn ?: it.loggedIn
            )
        }
    }

    fun login(tokenResponse: TokenResponse) {
        val decoded = JWT.decode(tokenResponse.accessToken)
        val role = UserRole.parse(decoded.getClaim("role").asArray(Authority::class.java)[0])
        val userId = decoded.getClaim("userId").asInt()

        sessionRepository.setAccessToken(tokenResponse.accessToken)
            .setRefreshToken(tokenResponse.refreshToken)
            .setExpiresIn(tokenResponse.expiresIn)
            .setUserId(userId)
            .setLoggedIn()

        _session.update {
            it.copy(
                accessToken = tokenResponse.accessToken,
                refreshToken = tokenResponse.refreshToken,
                expiresIn = tokenResponse.expiresIn,
                loggedIn = true,
                userRole = role,
                userId = userId
            )
        }
        Log.i(logTag, session.value.toString())
    }

    fun clearSession() {
        sessionRepository.clearSession()
        _session.update {
            it.copy(
                accessToken = "",
                refreshToken = "",
                expiresIn = 0L,
                loggedIn = false,
                userRole = null,
                userId = null
            )
        }
    }
}