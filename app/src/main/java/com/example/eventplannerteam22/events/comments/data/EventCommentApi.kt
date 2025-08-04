package com.example.eventplannerteam22.events.comments.data


import com.example.eventplannerteam22.events.comments.domain.EventComment
import retrofit2.http.*

data class CreateEventCommentRequest(
    val userId: Int,
    val eventId: Int,
    val text: String
)

interface EventCommentApi {
    @GET("/events/comments/{eventId}/approved")
    suspend fun getComments(@Path("eventId") eventId: Int): List<EventComment>

    @POST("/events/comments")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Body comment: CreateEventCommentRequest
    ): EventComment
}