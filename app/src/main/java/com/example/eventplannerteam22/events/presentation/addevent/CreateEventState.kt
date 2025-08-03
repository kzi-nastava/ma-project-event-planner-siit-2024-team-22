package com.example.eventplannerteam22.events.presentation.addevent

data class CreateEventState(
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
//    val eventType: EventType,
    val maxCapacity: String = "",
    val maxCapacityError: String? = null,
    val isPrivate: Boolean = false,
    val location: String = "",
    val locationError: String? = null,
//    val date: Date
//    val user
)
