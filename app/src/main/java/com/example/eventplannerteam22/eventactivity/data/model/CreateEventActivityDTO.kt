package com.example.eventplannerteam22.eventactivity.data.model

import com.squareup.moshi.JsonClass
import java.time.LocalTime

@JsonClass(generateAdapter = true)
data class CreateEventActivityDTO(
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String
)
