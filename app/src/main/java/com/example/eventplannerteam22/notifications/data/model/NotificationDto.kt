package com.example.eventplannerteam22.notifications.data.model

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class NotificationDto(
    val id: Int,
    val message: String,
    val timestamp: LocalDateTime
)