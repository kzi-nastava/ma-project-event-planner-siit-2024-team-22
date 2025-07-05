package com.example.eventplannerteam22.auth.registration

data class RegistrationScreenState(
    val isLoading: Boolean = false,
    val name: String = "",
    var nameErrorText: String? = null,
    val surname: String = "",
    val surnameErrorText: String? = null,
    val email: String = "",
    val emailErrorText: String? = null,
    val password: String = "",
    val passwordErrorText: String? = null,
    val role: String = "User"
)