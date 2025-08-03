package com.example.eventplannerteam22.eventactivity.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EventActivityDTO(
    val id: Int,
    val name: String
)
