package com.example.eventplannerteam22.events.domen

import com.example.eventplannerteam22.eventactivity.domen.EventActivity
import com.example.eventplannerteam22.events.data.model.UserDTO
import com.example.eventplannerteam22.eventtype.domen.EventType
import java.time.LocalDate

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val eventType: EventType,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
    val eventDate: LocalDate,
    val user: UserDTO,
    val eventActivities: List<EventActivity>
)