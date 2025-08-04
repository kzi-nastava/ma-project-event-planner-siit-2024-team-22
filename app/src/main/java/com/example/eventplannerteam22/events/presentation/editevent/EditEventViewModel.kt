package com.example.eventplannerteam22.events.presentation.editevent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.events.data.model.EventDTO
import com.example.eventplannerteam22.events.data.model.UpdateEventDTO
import com.example.eventplannerteam22.events.data.repository.EventRepository
import com.example.eventplannerteam22.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    var screenState by mutableStateOf(EditEventState())
        private set

    private val submitResultChannel = Channel<ApiResult<EventDTO>>()
    val submitResults = submitResultChannel.receiveAsFlow()

    fun loadInitialValues(event: UpdateEventDTO) {
        screenState = screenState.copy(
            name = event.name,
            description = event.description,
            maxCapacity = event.maxCapacity.toString(),
            location = event.location,
            eventDate = event.eventDate
        )
    }

    fun onEvent(event: EditEventUiEvent) {
        when (event) {
            is EditEventUiEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.input,
                    nameError = validateName(event.input)
                )
            }

            is EditEventUiEvent.DescriptionChanged -> {
                screenState = screenState.copy(
                    description = event.input,
                    descriptionError = validateDescription(event.input)
                )
            }

            is EditEventUiEvent.MaxCapacityChanged -> {
                if (event.input.all { it.isDigit() })
                    screenState = screenState.copy(
                        maxCapacity = event.input,
                        maxCapacityError = validateMaxCapacity(event.input)
                    )
            }

            is EditEventUiEvent.LocationChanged -> {
                screenState = screenState.copy(
                    location = event.input,
                    locationError = validateLocation(event.input)
                )
            }

            is EditEventUiEvent.Submit -> {
                if (isAllValid()) updateEvent(event.eventId)
            }
        }
    }

    private fun updateEvent(eventId: Int) {
        viewModelScope.launch {
            val dto = UpdateEventDTO(
                name = screenState.name,
                description = screenState.description,
                maxCapacity = screenState.maxCapacity.toInt(),
                location = screenState.location,
                eventDate = screenState.eventDate
            )

            submitResultChannel.send(eventRepository.editEvent(eventId, dto))
        }
    }

    private fun isAllValid(): Boolean {
        return listOf(
            screenState.nameError,
            screenState.descriptionError,
            screenState.maxCapacityError,
            screenState.locationError
        ).all { it == null }
    }

    private fun validateName(input: String): String? {
        return when {
            input.isBlank() -> "Name cannot be blank"
            input.length < 3 -> "Name cannot be shorter than 3"
            else -> null
        }
    }

    private fun validateDescription(input: String): String? {
        return when {
            input.isBlank() -> "Description cannot be blank"
            input.length < 3 -> "Description cannot be shorter than 10"
            else -> null
        }
    }

    private fun validateMaxCapacity(input: String): String? {
        return when {
            input.isBlank() -> "Max capacity cannot be blank"
            input.toInt() < 1 -> "Max capacity cannot be less than 1"
            else -> null
        }
    }

    private fun validateLocation(input: String): String? {
        return when {
            input.isBlank() -> "Location cannot be blank"
            else -> null
        }
    }
}