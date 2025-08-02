package com.example.eventplannerteam22.events.domen

import com.example.eventplannerteam22.events.data.model.UserDTO

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val eventTypeName: String,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
//    val eventDate: Date,
    val user: UserDTO
)