package com.example.eventplannerteam22.booking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.api.EventApi
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.solutions.data.SolutionApi
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingFormViewModel @Inject constructor(
    private val eventApi: EventApi,
    private val solutionApi: SolutionApi
) : ViewModel() {
    private val _events = MutableStateFlow<List<EventDTO>>(emptyList())
    val events: StateFlow<List<EventDTO>> = _events

    private val _services = MutableStateFlow<List<Solution>>(emptyList())
    val services: StateFlow<List<Solution>> = _services

    fun loadEvents() {
        viewModelScope.launch {
            _events.value = eventApi.getEvents(100, 0)
        }
    }

    fun loadServices() {
        viewModelScope.launch {
            _services.value = solutionApi.getSolutions(100, 0)
        }
    }
}
