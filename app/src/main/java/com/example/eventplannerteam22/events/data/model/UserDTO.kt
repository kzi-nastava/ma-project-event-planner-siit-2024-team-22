package com.example.eventplannerteam22.events.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    val id: Int,
    val name: String,
    val surname: String
)
