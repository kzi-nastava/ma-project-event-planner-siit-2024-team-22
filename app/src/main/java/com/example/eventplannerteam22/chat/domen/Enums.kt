package com.example.eventplannerteam22.chat.domen

enum class MessageType {
    CHAT,
    UNSEEN,
    FRIEND_ONLINE,
    FRIEND_OFFLINE,
    MESSAGE_DELIVERY_UPDATE
}

enum class MessageDeliveryStatusEnum {
    NOT_DELIVERED,
    DELIVERED,
    SEEN
}