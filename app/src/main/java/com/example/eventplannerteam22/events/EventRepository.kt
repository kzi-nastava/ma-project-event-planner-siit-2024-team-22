package com.example.eventplannerteam22.events


import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventApi: EventApi
) {
    suspend fun getEvents(limit: Int, offset: Int): List<Event> {
        val events = eventApi.getEvents(limit, offset)
        return events.filter { !it.isDeleted }
    }
}