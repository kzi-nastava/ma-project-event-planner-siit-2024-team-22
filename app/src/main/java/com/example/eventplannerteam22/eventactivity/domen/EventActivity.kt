package com.example.eventplannerteam22.eventactivity.domen

import java.time.LocalTime

data class EventActivity(
    val id: Int,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String
)
