package com.example.eventplannerteam22.events


import com.example.eventplannerteam22.products.Product
import javax.inject.Inject


class EventRepository @Inject constructor(
    private val eventApi: EventApi
) {
    suspend fun getEvents(limit: Int, offset: Int): List<Event> {
        val events = eventApi.getEvents(limit, offset)
        return events.filter { !it.isDeleted }
    }
    suspend fun getEventById(id: Int): Event {
        return eventApi.getEventById(id)
    }
    suspend fun addEvent(event: Event) {
        eventApi.addEvent(event)
    }
}