package com.example.eventplannerteam22.auth

data class AuthState(
    val isLoading: Boolean = false,
    val loginEmail: String = "",
    val loginPassword: String = "",
    val registrationName: String = "",
    val registrationSurname: String = "",
    val registrationEmail: String = "",
    val registrationPassword: String = "",
    val registrationRole: String = "User"
)