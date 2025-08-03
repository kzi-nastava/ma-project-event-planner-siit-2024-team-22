package com.example.eventplannerteam22.events.domen

import java.time.LocalDate

data class EventListItem(
    val id: Int,
    val name: String,
    val description: String,
    val eventTypeName: String,
    val location: String,
    val organizer: String,
    val eventDate: LocalDate
)
