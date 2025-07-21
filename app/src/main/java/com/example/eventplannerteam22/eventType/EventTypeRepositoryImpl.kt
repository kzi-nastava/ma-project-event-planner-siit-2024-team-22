package com.example.eventplannerteam22.eventType

import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.network.safeApiCall
import javax.inject.Inject

class EventTypeRepositoryImpl @Inject constructor(
    val eventTypeApi: EventTypeApi,
) : EventTypeRepository {
    override suspend fun getAllEventTypes(): ApiResult<List<EventType>> {
        return safeApiCall { eventTypeApi.getEventTypes() }
    }
}