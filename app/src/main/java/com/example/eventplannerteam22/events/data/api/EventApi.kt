package com.example.eventplannerteam22.events.data.api

import com.example.eventplannerteam22.events.data.model.CreateEventRequest
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.domen.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("/events")
    suspend fun getEvents(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<EventDTO>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): Event

    @POST("/events")
    suspend fun addEvent(@Body eventRequest: CreateEventRequest)
}