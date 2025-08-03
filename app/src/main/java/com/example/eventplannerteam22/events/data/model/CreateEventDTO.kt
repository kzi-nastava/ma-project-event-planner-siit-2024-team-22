package com.example.eventplannerteam22.events.data.model

import java.time.LocalDate

data class CreateEventDTO(
    val name: String,
    val description: String,
    val eventTypeId: Int,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
    val dateOfEvent: LocalDate,
    val userId: Int
)