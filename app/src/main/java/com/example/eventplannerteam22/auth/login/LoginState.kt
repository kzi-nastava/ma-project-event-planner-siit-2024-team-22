package com.example.eventplannerteam22.auth.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailErrorText: String? = null,
    val password: String = "",
    val passwordErrorText: String? = null
    )