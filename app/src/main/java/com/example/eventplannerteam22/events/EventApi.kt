package com.example.eventplannerteam22.events

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EventApi {
    @GET("/events")
    suspend fun getEvents(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Event>
}