package com.example.eventplannerteam22.eventType

import retrofit2.http.GET

interface EventTypeApi {
    @GET("/event_type")
    suspend fun getEventTypes(): List<EventType>
}