package com.example.eventplannerteam22.events.data.model

import com.example.eventplannerteam22.eventType.data.model.EventTypeDTO

data class EventDTO(
    val id: Int,
    val name: String,
    val description: String,
    val eventType: EventTypeDTO,
    val maxCapacity: Int,
    val isPrivate: Boolean,
    val location: String,
//    val eventDate: Date,
    val user: UserDTO
//    val solutions: List<SolutionDTO>
//    val products: List<ProductDTO>
//    val eventActivities: List<EventActivityDTO>
)