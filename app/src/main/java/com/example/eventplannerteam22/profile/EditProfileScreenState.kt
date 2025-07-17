package com.example.eventplannerteam22.profile

data class EditProfileScreenState(
    val name: String = "",
    val nameErrorText: String? = null,

    val surname: String = "",
    val surnameErrorText: String? = null,

    val email: String = "",
    val emailErrorText: String? = null,

    val phone: String = "",
    val phoneErrorText: String? = null,

    val address: String = "",
    val addressErrorText: String? = null,

    val password: String = "",
    val passwordErrorText: String? = null
)