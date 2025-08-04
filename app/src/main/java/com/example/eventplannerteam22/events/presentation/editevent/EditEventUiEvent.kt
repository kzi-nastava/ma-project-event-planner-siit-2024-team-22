package com.example.eventplannerteam22.events.presentation.editevent

sealed class EditEventUiEvent {
    data class NameChanged(val input: String) : EditEventUiEvent()
    data class DescriptionChanged(val input: String) : EditEventUiEvent()
    data class MaxCapacityChanged(val input: String) : EditEventUiEvent()
    data class LocationChanged(val input: String) : EditEventUiEvent()
    data class Submit(val eventId: Int) : EditEventUiEvent()
}