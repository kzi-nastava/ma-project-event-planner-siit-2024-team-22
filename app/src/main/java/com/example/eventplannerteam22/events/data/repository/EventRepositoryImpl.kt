package com.example.eventplannerteam22.events.data.repository

import com.example.eventplannerteam22.events.data.api.EventApi
import com.example.eventplannerteam22.events.data.model.CreateEventDTO
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.data.model.UpdateEventDTO
import com.example.eventplannerteam22.events.data.toEvent
import com.example.eventplannerteam22.events.data.toListItem
import com.example.eventplannerteam22.events.domen.Event
import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import okhttp3.OkHttpClient
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val okHttpClient: OkHttpClient
) : EventRepository {
    override suspend fun getEvents(limit: Int, offset: Int): ApiResult<List<EventListItem>> {
        return safeApiCall(okHttpClient) { eventApi.getEvents(limit, offset).map { it.toListItem() } }
    }

    override suspend fun getEventById(id: Int): ApiResult<Event> {
        return safeApiCall(okHttpClient) { eventApi.getEventById(id).toEvent() }
    }

    override suspend fun addEvent(dto: CreateEventDTO) {
        eventApi.addEvent(dto)
    }


    override suspend fun editEvent(eventId: Int, dto: UpdateEventDTO): ApiResult<EventDTO> {
        return safeApiCall(okHttpClient) { eventApi.editEvent(eventId, dto) }
    }
}