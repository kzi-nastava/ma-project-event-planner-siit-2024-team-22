package com.example.eventplannerteam22.eventtype.presentation.eventtypedetails

import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.events.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventTypeDetailsViewModel @Inject constructor(
    eventRepository: EventRepository
) : ViewModel() {

}
