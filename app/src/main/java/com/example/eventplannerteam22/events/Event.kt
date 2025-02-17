package com.example.eventplannerteam22.events

import com.example.eventplannerteam22.solutions.Solution
data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val eventType: EventType,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
    val dateOfEvent: String,
    val isDeleted: Boolean,
    val solution: Solution?
)

data class EventType(
    val id: Int,
    val name: String,
    val description: String
)