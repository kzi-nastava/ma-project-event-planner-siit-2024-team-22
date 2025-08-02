package com.example.eventplannerteam22.events.presentation.eventlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.repository.EventRepositoryImpl
import com.example.eventplannerteam22.events.domen.EventListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventRepositoryImpl
) : ViewModel() {

    var events by mutableStateOf<List<EventListItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreEvents by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3

    init {
        loadEvents()
    }

    fun loadEvents() {
        if (isLoading || !hasMoreEvents) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newEvents = repository.getEvents(limit, currentOffset)
                events = events + newEvents
                currentOffset += limit


                hasMoreEvents = newEvents.size == limit
            } catch (e: Exception) {

                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}