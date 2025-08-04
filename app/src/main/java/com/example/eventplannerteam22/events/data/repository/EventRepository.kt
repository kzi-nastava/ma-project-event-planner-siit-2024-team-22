package com.example.eventplannerteam22.events.data.repository

import com.example.eventplannerteam22.events.data.model.CreateEventDTO
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.network.ApiResult

interface EventRepository {
    suspend fun getEvents(limit: Int, offset: Int): ApiResult<List<EventListItem>>
    suspend fun getEventById(id: Int): ApiResult<Event>
    suspend fun addEvent(dto: CreateEventDTO)

}