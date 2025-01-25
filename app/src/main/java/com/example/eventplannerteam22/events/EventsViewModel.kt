package com.example.eventplannerteam22.ui.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.Event
import com.example.eventplannerteam22.events.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    var events by mutableStateOf<List<Event>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var hasMoreEvents by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val limit = 3 // Количество событий, загружаемых за раз

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

                // Если загружено меньше событий, чем запрошено, значит, больше событий нет
                hasMoreEvents = newEvents.size == limit
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}