package com.example.eventplannerteam22.events.presentation.eventlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.events.domen.EventListItem
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

    private val fetchResultChannel = Channel<ApiResult<List<EventListItem>>>()
    val fetchResults = fetchResultChannel.receiveAsFlow()

    fun fetchEvents() {
        viewModelScope.launch {
            screenState.copy(isLoading = true)
            val result = eventRepository.getEvents(5, 0)
            fetchResultChannel.send(result)
        }
    }

    fun loadEvents(result: ApiResult<List<EventListItem>>) {
        when (result) {
            is ApiResult.Success -> {
                Log.d("EventListViewModel", result.data.toString())
                screenState = screenState.copy(events = result.data)
            }

            else -> Unit
        }
    }
}