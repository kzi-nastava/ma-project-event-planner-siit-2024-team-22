package com.example.eventplannerteam22.eventType.data.repository

import com.example.eventplannerteam22.eventType.domen.EventTypeListItem
import com.example.eventplannerteam22.network.ApiResult

interface EventTypeRepository {
    suspend fun getAllEventTypes(): ApiResult<List<EventTypeListItem>>
    suspend fun createEventType(name: String, description: String): ApiResult<Unit>
}