package com.example.eventplannerteam22.chat.domen

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class ChatMessage(
    val id: UUID = UUID.randomUUID(),
    val content: String,
    val messageType: MessageType,
    val senderId: Int? = null,
    val senderUsername: String? = null,
    val receiverId: Int? = null,
    val receiverUsername: String? = null,
    val userConnection: UserConnection? = null,
    val messageDeliveryStatusEnum: MessageDeliveryStatusEnum? = null,
    val messageDeliveryStatusUpdates: List<MessageDeliveryStatusUpdate>? = null
)