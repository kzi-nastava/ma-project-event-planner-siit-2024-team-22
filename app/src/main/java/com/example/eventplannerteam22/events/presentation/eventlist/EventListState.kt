package com.example.eventplannerteam22.events.presentation.eventlist

import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.events.presentation.filters.EventFilterState

data class EventListState(
    val isLoading: Boolean = false,
    val events: List<EventListItem> = emptyList<EventListItem>(),
    val filteredEvents: List<EventListItem> = emptyList<EventListItem>(),
    val isFiltered: Boolean = false,
    val filterState: EventFilterState = EventFilterState()
)
