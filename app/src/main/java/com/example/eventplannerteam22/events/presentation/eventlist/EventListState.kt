package com.example.eventplannerteam22.events.presentation.eventlist

import com.example.eventplannerteam22.events.domen.EventListItem

data class EventListState(
    val isLoading: Boolean = false,
    val events: List<EventListItem> = emptyList<EventListItem>()
)
