package com.example.eventplannerteam22.auth.login.presentation

sealed class LoginScreenState {
    object Idle : LoginScreenState()
    object Loading : LoginScreenState()
    data class Success(val token: String) : LoginScreenState()
    data class Error(val message: String) : LoginScreenState()
}