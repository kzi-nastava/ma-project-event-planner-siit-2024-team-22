package com.example.eventplannerteam22.eventType

import com.example.eventplannerteam22.network.ApiResult

interface EventTypeRepository {
    suspend fun getAllEventTypes(): ApiResult<List<EventType>>
    suspend fun createEventType(name: String, description: String): ApiResult<Unit>
}