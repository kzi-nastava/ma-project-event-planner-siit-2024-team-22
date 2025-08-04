package com.example.eventplannerteam22.events.presentation.addevent

import com.example.eventplannerteam22.eventactivity.data.model.CreateEventActivityDTO
import java.time.LocalDate

sealed class CreateEventUiEvent {
    data class NameChanged(val input: String) : CreateEventUiEvent()
    data class DescriptionChanged(val input: String) : CreateEventUiEvent()
    data class EventTypeIdChanged(val input: Int) : CreateEventUiEvent()
    data class MaxCapacityChanged(val input: String) : CreateEventUiEvent()
    data class IsPrivateChanged(val input: Boolean) : CreateEventUiEvent()
    data class LocationChanged(val input: String) : CreateEventUiEvent()
    data class DateOfEventChanged(val input: LocalDate) : CreateEventUiEvent()
    object Submit : CreateEventUiEvent()
    data class AddActivity(val activity: CreateEventActivityDTO) : CreateEventUiEvent()
    data class RemoveActivity(val index: Int) : CreateEventUiEvent()
}
