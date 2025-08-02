package com.example.eventplannerteam22.events.data.repository

import com.example.eventplannerteam22.events.data.api.EventApi
import com.example.eventplannerteam22.events.data.mappers.toListItem
import com.example.eventplannerteam22.events.data.model.CreateEventRequest
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : EventRepository {
    override suspend fun getEvents(limit: Int, offset: Int): List<EventListItem> {
        return eventApi.getEvents(limit, offset).filter { dto -> !dto.isPrivate }.map { dto -> dto.toListItem() }
    }

    override suspend fun getEventById(id: Int): Event {
        return eventApi.getEventById(id)
    }

    override suspend fun addEvent(eventRequest: CreateEventRequest) {
        eventApi.addEvent(eventRequest)
    }
}