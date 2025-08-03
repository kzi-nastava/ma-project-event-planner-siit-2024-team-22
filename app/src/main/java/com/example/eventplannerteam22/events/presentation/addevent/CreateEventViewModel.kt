package com.example.eventplannerteam22.events.presentation.addevent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    eventRepository: EventRepository
) : ViewModel() {
    var screenState by mutableStateOf(CreateEventState())
        private set

    private val submitResultsChannel = Channel<ApiResult<Unit>>()

    val submitResults = submitResultsChannel.receiveAsFlow()

//    fun onEvent(event: CreateEventUiEvent) {
//    }
}
