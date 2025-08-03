package com.example.eventplannerteam22.solutions.domain

import com.example.eventplannerteam22.solutionCategory.domain.SolutionCategory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Duration
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Solution(
    val id: Int,
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
//    val createdBy: Int, TODO: поменять на Profile
    @Json(name = "deleted") val isDeleted: Boolean
)

enum class ServiceBookingConfirmType {
    Auto,
    Manual
}