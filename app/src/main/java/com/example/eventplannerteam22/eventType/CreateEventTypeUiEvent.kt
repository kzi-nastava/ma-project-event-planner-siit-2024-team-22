package com.example.eventplannerteam22.eventType

sealed class CreateEventTypeUiEvent {
    data class NameChanged(val value: String) : CreateEventTypeUiEvent()
    data class DescriptionChanged(val value: String) : CreateEventTypeUiEvent()
    object Submit : CreateEventTypeUiEvent()
}
