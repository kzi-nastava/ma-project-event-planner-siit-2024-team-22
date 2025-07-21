package com.example.eventplannerteam22.eventType

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateEventTypeViewModel @Inject constructor(
    eventTypeRepository: EventTypeRepository
) : ViewModel() {
    var screenState by mutableStateOf(CreateEventTypeState())
        private set
}