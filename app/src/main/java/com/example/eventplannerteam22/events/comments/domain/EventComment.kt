package com.example.eventplannerteam22.events.comments.domain

data class EventComment(
    val id: Int,
    val text: String,
    val approved: Boolean,
    val authorId: Int,
    val authorName: String,
    val authorSurname: String,
    val eventId: Int
) {
    val authorFullName: String
        get() = "$authorName $authorSurname"
}