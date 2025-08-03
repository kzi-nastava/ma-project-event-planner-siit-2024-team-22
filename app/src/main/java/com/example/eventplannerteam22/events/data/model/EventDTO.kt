package com.example.eventplannerteam22.events.data.model

import com.example.eventplannerteam22.eventactivity.data.model.EventActivityDTO
import com.example.eventplannerteam22.eventtype.data.model.EventTypeDTO
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class EventDTO(
    val id: Int,
    val name: String,
    val description: String,
    val eventType: EventTypeDTO,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
    val eventDate: LocalDate,
    val user: UserDTO,
    val eventActivities: List<EventActivityDTO>
)