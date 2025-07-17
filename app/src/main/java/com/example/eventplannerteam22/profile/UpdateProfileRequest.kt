package com.example.eventplannerteam22.profile

data class UpdateProfileRequest(
    val id: Int,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val homeAddress: String? = null
)
