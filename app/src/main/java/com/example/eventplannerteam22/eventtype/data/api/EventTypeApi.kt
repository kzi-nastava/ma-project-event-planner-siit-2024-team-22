package com.example.eventplannerteam22.eventtype.data.api

import com.example.eventplannerteam22.eventtype.data.model.CreateEventTypeRequest
import com.example.eventplannerteam22.eventtype.data.model.EventTypeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventTypeApi {
    @GET("/event_type")
    suspend fun getEventTypes(): List<EventTypeDTO>

    @GET("/event_type/{id}")
    suspend fun getEventType(id: Int): EventTypeDTO

    @POST("/event_type")
    suspend fun createEventType(@Body request: CreateEventTypeRequest)
}