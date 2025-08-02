package com.example.eventplannerteam22.eventType.presentation.eventtypelist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.eventType.data.repository.EventTypeRepository
import com.example.eventplannerteam22.eventType.domen.EventTypeListItem
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class EventTypesViewModel @Inject constructor(
    private val eventTypeRepository: EventTypeRepository
) : ViewModel() {
    var screenState by mutableStateOf(EventTypesScreenState())
        private set
    private var loadEventTypesResultChannel = Channel<ApiResult<List<EventTypeListItem>>>()
    val loadEventTypesResults = loadEventTypesResultChannel.receiveAsFlow()

    var eventTypes by mutableStateOf<List<EventTypeListItem>>(emptyList())
        private set

    suspend fun fetchEventTypes() {
        screenState.isLoading = true
        val result = eventTypeRepository.getAllEventTypes()
        loadEventTypesResultChannel.send(result)
        screenState.isLoading = false
    }

    fun loadEventTypes(result: ApiResult<List<EventTypeListItem>>) {
        when (result) {
            is ApiResult.Success -> {
                eventTypes = result.data
            }

            else -> Unit
        }
    }
}