package com.example.eventplannerteam22.events.presentation.eventlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.events.domen.EventListItem
import com.example.eventplannerteam22.events.presentation.filters.EventFilterState
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    var screenState by mutableStateOf(EventListState())
        private set
    
    var events by mutableStateOf<List<EventListItem>>(emptyList())
        private set

    private val fetchResultChannel = Channel<ApiResult<List<EventListItem>>>()
    val fetchResults = fetchResultChannel.receiveAsFlow()

    fun fetchEvents() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = eventRepository.getEvents(5, 0)
            fetchResultChannel.send(result)
        }
    }

    fun loadEvents(result: ApiResult<List<EventListItem>>) {
        when (result) {
            is ApiResult.Success -> {
                Log.d("EventListViewModel", result.data.toString())
                events = result.data
                screenState = screenState.copy(
                    events = result.data,
                    filteredEvents = result.data,
                    isFiltered = false
                )
            }

            else -> Unit
        }
    }

    fun updateFilterState(newFilterState: EventFilterState) {
        screenState = screenState.copy(filterState = newFilterState)
    }

    fun applyFilters() {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            
            val filterState = screenState.filterState
            val result = eventRepository.searchAndFilterEvents(
                name = filterState.name.takeIf { it.isNotEmpty() },
                location = filterState.location.takeIf { it.isNotEmpty() },
                eventType = filterState.eventType.takeIf { it.isNotEmpty() },
                fromDate = null,
                toDate = null,
                maxCapacity = filterState.maxCapacity.toIntOrNull(),
                isPrivate = when (filterState.isPrivate) {
                    "Yes" -> true
                    "No" -> false
                    else -> null
                }
            )
            
            when (result) {
                is ApiResult.Success -> {
                    screenState = screenState.copy(
                        filteredEvents = result.data,
                        isFiltered = true,
                        isLoading = false
                    )
                }
                else -> {
                    screenState = screenState.copy(isLoading = false)
                }
            }
        }
    }

    fun clearFilters() {
        screenState = screenState.copy(
            filterState = EventFilterState(),
            filteredEvents = screenState.events,
            isFiltered = false
        )
    }
}