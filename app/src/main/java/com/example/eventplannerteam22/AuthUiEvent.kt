package com.example.eventplannerteam22

sealed class AuthUiEvent {
    data class LoginEmailChanged(val value: String): AuthUiEvent()
    data class LoginPasswordChanged(val value: String): AuthUiEvent()
    object Login: AuthUiEvent()
}