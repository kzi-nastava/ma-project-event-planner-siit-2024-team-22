package com.example.eventplannerteam22.eventType.data.repository

import com.example.eventplannerteam22.eventType.data.api.EventTypeApi
import com.example.eventplannerteam22.eventType.data.model.CreateEventTypeRequest
import com.example.eventplannerteam22.eventType.data.toEventTypeListItem
import com.example.eventplannerteam22.eventType.domen.EventTypeListItem
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