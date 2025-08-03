package com.example.eventplannerteam22.solutions.domain

import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import java.time.Duration
import java.time.LocalDate

data class CreateSolutionDTO (
    val name: String,
    val category: SolutionCategory,
    val description: String,
    val features: String,
    val price: Double,
    val discount: Double,
    val imgUrl: String,
    val visible: Boolean,
    val duration: Duration,
    val dateStartBooking: LocalDate,
    val dateFinishBooking: LocalDate,
    val bookingConfirmType: ServiceBookingConfirmType,
    val createdBy: Int,
    val isDeleted: Boolean
)
