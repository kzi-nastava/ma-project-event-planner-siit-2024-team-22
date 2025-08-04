package com.example.eventplannerteam22.events.presentation.editevent

import java.time.LocalDate

data class EditEventState(
    val name: String = "",
    val nameError: String? = null,

    val description: String = "",
    val descriptionError: String? = null,

    val maxCapacity: String = "",
    val maxCapacityError: String? = null,

    val location: String = "",
    val locationError: String? = null,

    val eventDate: LocalDate = LocalDate.now()
)