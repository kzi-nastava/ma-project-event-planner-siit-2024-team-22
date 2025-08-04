package com.example.eventplannerteam22.chat.domen

import com.squareup.moshi.Json

data class UserConnection(
    val connectionId: Int? = null,
    val connectionUsername: String? = null,
    val convId: String? = null,
    val unSeen: Int = 0,
    @Json(name = "isOnline") val isOnline: Boolean = false
)