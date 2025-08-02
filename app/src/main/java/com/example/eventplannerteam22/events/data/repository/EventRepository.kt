package com.example.eventplannerteam22.events.data.repository

import com.example.eventplannerteam22.events.data.model.CreateEventRequest
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem

interface EventRepository {
    suspend fun getEvents(limit: Int, offset: Int): List<EventListItem>
    suspend fun getEventById(id: Int): Event
    suspend fun addEvent(eventRequest: CreateEventRequest)
}