package com.example.eventplannerteam22.events.comments.data

import com.example.eventplannerteam22.events.comments.domain.EventComment
import javax.inject.Inject

class EventCommentRepository @Inject constructor(
    private val api: EventCommentApi
) {
    suspend fun getComments(eventId: Int): List<EventComment> {
        return api.getComments(eventId)
    }

    suspend fun addComment(token: String, userId: Int, eventId: Int, text: String): EventComment {
        return api.addComment("Bearer $token", CreateEventCommentRequest(userId, eventId, text))
    }
}