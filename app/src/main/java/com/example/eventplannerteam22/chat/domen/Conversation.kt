package com.example.eventplannerteam22.chat.domen

data class Conversation(
    val id: String,
    val participant1Id: Int,
    val participant2Id: Int,
    val lastMessage: String? = null,
    val lastMessageTime: Long? = null
)