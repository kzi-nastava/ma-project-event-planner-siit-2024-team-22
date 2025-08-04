package com.example.eventplannerteam22.chat.domen

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class MessageDeliveryStatusUpdate(
    val id: UUID,
    val content: String? = null,
    val messageDeliveryStatusEnum: MessageDeliveryStatusEnum
)