package com.example.eventplannerteam22.session

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _session = MutableStateFlow<Session>(Session())
    var session = _session.asStateFlow()

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

    fun clearSession() {
        sessionRepository.clearSession()
        _session.update {
            it.copy(
                accessToken = "",
                refreshToken = "",
                expiresIn = 0L,
                loggedIn = false
            )
        }
    }
}