package com.example.eventplannerteam22.solutions

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Duration
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Solution(
    val id: Int,
    val name: String,
    val category: Category,
    val description: String,
    val features: String?,
    val price: Double,
    val discount: Double,
    val imgUrl: String?,
    val visible: Boolean,
    val duration: Duration,
    val dateStartBooking: LocalDate,
    val dateFinishBooking: LocalDate,
    val bookingConfirmType: String,
    @Json(name = "deleted") val isDeleted: Boolean
)

@JsonClass(generateAdapter = true)
data class Category(
    val id: Int,
    val name: String,
    val categoryType: String
)