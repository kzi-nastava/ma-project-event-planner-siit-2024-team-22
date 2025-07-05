package com.example.eventplannerteam22.session

data class Session(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresIn: Long = 0L,
    val loggedIn: Boolean = false
)