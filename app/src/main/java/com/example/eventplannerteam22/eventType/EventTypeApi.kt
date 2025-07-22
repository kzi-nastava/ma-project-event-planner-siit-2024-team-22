package com.example.eventplannerteam22.eventType

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventTypeApi {
    @GET("/event_type")
    suspend fun getEventTypes(): List<EventType>

    @POST("/event_type")
    suspend fun createEventType(@Body request: CreateEvetTypeRequest)
}