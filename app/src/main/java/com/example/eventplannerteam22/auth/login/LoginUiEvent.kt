package com.example.eventplannerteam22.auth.login

sealed class LoginUiEvent {
    data class LoginEmailChanged(val value: String): LoginUiEvent()
    data class LoginPasswordChanged(val value: String): LoginUiEvent()
    object Login: LoginUiEvent()
}