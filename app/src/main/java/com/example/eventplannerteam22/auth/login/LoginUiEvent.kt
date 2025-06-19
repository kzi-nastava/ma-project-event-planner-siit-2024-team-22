package com.example.eventplannerteam22.auth.login

sealed class LoginUiEvent {
    data class EmailChanged(val value: String): LoginUiEvent()
    data class PasswordChanged(val value: String): LoginUiEvent()
    object Login: LoginUiEvent()
}