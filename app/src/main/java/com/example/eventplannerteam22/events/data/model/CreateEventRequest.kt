package com.example.eventplannerteam22.events.data.model

data class CreateEventRequest(
    val name: String,
    val description: String,
    val eventType: EventTypeIdDto,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
    val dateOfEvent: String,
    val isDeleted: Boolean,
    val solution: SolutionIdDto
)

data class EventTypeIdDto(val id: Int)
data class SolutionIdDto(val id: Int)