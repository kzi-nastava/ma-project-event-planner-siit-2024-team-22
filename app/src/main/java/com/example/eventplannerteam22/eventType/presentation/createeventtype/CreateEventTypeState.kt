package com.example.eventplannerteam22.eventType.presentation.createeventtype

data class CreateEventTypeState(
    val name: String = "",
    val nameError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val isLoading: Boolean = false
)