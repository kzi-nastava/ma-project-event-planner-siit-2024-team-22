package com.example.eventplannerteam22.eventType.data.api

import com.example.eventplannerteam22.eventType.data.model.CreateEventTypeRequest
import com.example.eventplannerteam22.eventType.data.model.EventTypeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventTypeApi {
    @GET("/event_type")
    suspend fun getEventTypes(): List<EventTypeDTO>

    @POST("/event_type")
    suspend fun createEventType(@Body request: CreateEventTypeRequest)
}