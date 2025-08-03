package com.example.eventplannerteam22.eventtype.data.repository

import com.example.eventplannerteam22.eventtype.data.api.EventTypeApi
import com.example.eventplannerteam22.eventtype.data.model.CreateEventTypeRequest
import com.example.eventplannerteam22.eventtype.data.toEventType
import com.example.eventplannerteam22.eventtype.data.toEventTypeListItem
import com.example.eventplannerteam22.eventtype.domen.EventType
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import okhttp3.OkHttpClient
import javax.inject.Inject

class EventTypeRepositoryImpl @Inject constructor(
    val eventTypeApi: EventTypeApi,
    val okHttpClient: OkHttpClient
) : EventTypeRepository {
    override suspend fun getAllEventTypes(): ApiResult<List<EventTypeListItem>> {
        return safeApiCall(okHttpClient) { eventTypeApi.getEventTypes().map { it.toEventTypeListItem() } }
    }

    override suspend fun getEventType(id: Int): ApiResult<EventType> {
        return safeApiCall(okHttpClient) { eventTypeApi.getEventType(id).toEventType() }
    }

    override suspend fun createEventType(name: String, description: String): ApiResult<Unit> {
        return safeApiCall(okHttpClient) {
            eventTypeApi.createEventType(
                CreateEventTypeRequest(
                    name,
                    description
                )
            )
        }
    }
}