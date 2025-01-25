package com.example.eventplannerteam22.events

data class Event(
    val id: Int,
    val name: String,
    val maxCapacity: Int,
    val dateOfEvent: String,
    val description: String,
    val location: String,
    val isDeleted: Boolean
)
