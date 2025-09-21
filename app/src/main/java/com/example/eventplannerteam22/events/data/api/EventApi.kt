package com.example.eventplannerteam22.events.data.api

import com.example.eventplannerteam22.events.data.model.CreateEventDTO
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.data.model.UpdateEventDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface EventApi {
    @GET("/events")
    suspend fun getEvents(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<EventDTO>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventDTO

    @POST("/events")
    suspend fun addEvent(@Body eventRequest: CreateEventDTO)

    @PUT("/events/{eventId}")
    suspend fun editEvent(@Path("eventId") eventId: Int, @Body dto: UpdateEventDTO): EventDTO

    @GET("/events/search")
    suspend fun searchAndFilterEvents(
        @Query("name") name: String? = null,
        @Query("location") location: String? = null,
        @Query("eventType") eventType: String? = null,
        @Query("fromDate") fromDate: String? = null,
        @Query("toDate") toDate: String? = null,
        @Query("maxCapacity") maxCapacity: Int? = null,
        @Query("isPrivate") isPrivate: Boolean? = null
    ): List<EventDTO>
}