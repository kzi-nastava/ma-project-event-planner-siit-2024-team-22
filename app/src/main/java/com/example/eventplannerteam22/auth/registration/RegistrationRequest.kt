package com.example.eventplannerteam22.auth.registration

data class RegistrationRequest (
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String
)