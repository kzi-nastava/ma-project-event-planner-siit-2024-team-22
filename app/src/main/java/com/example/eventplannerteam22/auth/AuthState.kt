package com.example.eventplannerteam22.auth

data class AuthState(
    val isLoading: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = ""
)