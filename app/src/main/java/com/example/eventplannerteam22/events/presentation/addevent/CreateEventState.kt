package com.example.eventplannerteam22.events.presentation.addevent

import com.example.eventplannerteam22.eventactivity.data.model.CreateEventActivityDTO
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import java.time.LocalDate

data class CreateEventState(
    val name: String = "",
    val nameError: String? = null,

    val description: String = "",
    val descriptionError: String? = null,

    val eventTypeId: Int = -1,
    val eventTypeError: String? = null,
    val availableEventTypes: List<EventTypeListItem> = emptyList(),

    val maxCapacity: String = "",
    val maxCapacityError: String? = null,

    val isPrivate: Boolean = false,

    val location: String = "",
    val locationError: String? = null,

    val dateOfEvent: LocalDate = LocalDate.now(),

    val eventActivities: List<CreateEventActivityDTO> = emptyList()
)
