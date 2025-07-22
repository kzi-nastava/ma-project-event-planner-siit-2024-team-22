package com.example.eventplannerteam22.eventType

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import okhttp3.OkHttpClient
import javax.inject.Inject

class EventTypeRepositoryImpl @Inject constructor(
    val eventTypeApi: EventTypeApi,
    val okHttpClient: OkHttpClient
) : EventTypeRepository {
    override suspend fun getAllEventTypes(): ApiResult<List<EventType>> {
        return safeApiCall(okHttpClient) { eventTypeApi.getEventTypes() }
    }

    override suspend fun createEventType(name: String, description: String): ApiResult<Unit> {
        return safeApiCall(okHttpClient) {
            eventTypeApi.createEventType(
                CreateEvetTypeRequest(
                    name,
                    description
                )
            )
        }
    }
}