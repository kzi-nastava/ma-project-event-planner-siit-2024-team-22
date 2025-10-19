package com.example.eventplannerteam22.booking.domain.model

import com.example.eventplannerteam22.event.domain.model.Event
import com.example.eventplannerteam22.solution.domain.model.Service
import com.example.eventplannerteam22.user.domain.model.User
import java.time.LocalTime


data class Booking(
    val id: Int,
    val user: User,
    val event: Event,
    val services: List<Service>,
    val bookingDate: String,
    val startTime: String,
    val endTime: String,
    val confirmed: Boolean = false
)

