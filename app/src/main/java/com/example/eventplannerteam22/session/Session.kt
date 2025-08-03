package com.example.eventplannerteam22.session

import kotlin.reflect.full.memberProperties

data class Session(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresIn: Long = 0L,
    val loggedIn: Boolean = false,
    val userRole: UserRole? = null,
    val userId: Int? = null
) {
    override fun toString(): String {
        return buildString {
            append("User session:")
            Session::class.memberProperties.forEach { prop ->
                append("\n\t${prop.name}: ${prop.get(this@Session)}")
            }
        }
    }
}